package haken.dokemi.andoku.ui.ui.profile

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import haken.dokemi.andoku.R
import haken.dokemi.andoku.base.BaseActivity
import haken.dokemi.andoku.ui.ui.profile.path.URIPathHelper
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

class ProfileEditActivity : BaseActivity<ProfilesViewModel>() {

    lateinit var textInputEditTextDate: TextInputEditText
    lateinit var imageViewUser: CircleImageView
    lateinit var imageViewPhoto: CircleImageView

    lateinit var textInputEditTextName: TextInputEditText
    lateinit var textInputEditTextEmail: TextInputEditText
    lateinit var textInputEditNickName: TextInputEditText
    lateinit var buttonUpdate: Button
    lateinit var textViewNickName: TextView

    var mUri: Uri? = null

    lateinit var imageEditProfileBack: ImageView

    override fun onCreateStart(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_profile_edit)
        supportActionBar?.hide()
    }

    override fun setupViewComponent() {

    }

    override fun setupViewModel(): ProfilesViewModel {
        return ViewModelProvider(this)[ProfilesViewModel::class.java]
    }

    override fun setupView() {
        imageEditProfileBack = findViewById(R.id.image_view_back)
        textInputEditTextDate = findViewById(R.id.text_input_edit_text_date)
        textInputEditTextName = findViewById(R.id.text_input_edit_text_name)
        textInputEditTextEmail = findViewById(R.id.text_input_edit_text_email)
        textInputEditNickName = findViewById(R.id.text_input_edit_text_nick_name)
        buttonUpdate = findViewById(R.id.button_update)
        textViewNickName = findViewById(R.id.text_view_nick_name)
        imageViewUser = findViewById(R.id.image_view_user)
        imageViewPhoto = findViewById(R.id.image_view_photo)

        buttonUpdate.setOnClickListener {
            val strNickName = textInputEditNickName.text.toString().trim()
            val strName = textInputEditTextName.text.toString().trim()
            val strDateBirth = textInputEditTextDate.text.toString().trim()
            val strEmail = textInputEditTextEmail.text.toString().trim()

            val requestNickName =
                RequestBody.create(MediaType.parse("multipart/form-data"), strNickName)
            val requestName =
                RequestBody.create(MediaType.parse("multipart/form-data"), strName)
            val requestDateBirth =
                RequestBody.create(MediaType.parse("multipart/form-data"), strDateBirth)
            val requestEmail =
                RequestBody.create(MediaType.parse("multipart/form-data"), strEmail)

            if (mUri == null) {
                mViewModel.getUserUpdate2(
                    requestNickName,
                    requestName,
                    requestDateBirth,
                    requestEmail
                )
            } else {
                val strRealPath =
                    URIPathHelper().getRealPathFromURI(this, mUri)
                val file = File(strRealPath)
                val requestAvatar = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                val avatar =
                    MultipartBody.Part.createFormData("avatar", file.name, requestAvatar)

                mViewModel.updateProfile(
                    requestNickName,
                    requestName,
                    requestDateBirth,
                    requestEmail,
                    avatar
                )
            }

        }

        imageEditProfileBack.setOnClickListener { finish() }

        imageViewPhoto.setOnClickListener {
            setupSelectImageLocal()
        }

        //set up edit date birth
        setupEditTextViewDate()

        mViewModel.callProfile()
    }

    override fun setupCommonBinding() {

    }

    override fun setupBinding() {
        mViewModel.showUserUpdate.observe(this) { showUserUpdate ->
            if (showUserUpdate.code_status == 1) {
                val intent = Intent()

                val tmpName = textInputEditTextName.text.toString().trim()
                val tmpNickname = textInputEditNickName.text.toString().trim()
                val tmpDate = textInputEditTextDate.text.toString().trim()
                val tmpEmail = textInputEditTextEmail.text.toString().trim()

                intent.putExtra("NICKNAMED", tmpNickname)
                intent.putExtra("NAMED", tmpName)
                intent.putExtra("DATED", tmpDate)
                intent.putExtra("EMAILED", tmpEmail)

                if (mUri != null) {
                    intent.putExtra("AVATARED", mUri.toString())
                }

                setResult(Activity.RESULT_OK, intent)
                finish()

            } else {
                Toast.makeText(this, showUserUpdate.message, Toast.LENGTH_SHORT).show()
            }

        }

        //display edit user
        mViewModel.showUserProfile.observe(this) {
            if (it.code_status == 1) {
                textViewNickName.text = it.data.nick_name
                textInputEditNickName.setText(it.data.nick_name)
                textInputEditTextName.setText(it.data.name)
                textInputEditTextDate.setText(it.data.date_of_birth)
                textInputEditTextEmail.setText(it.data.email)
                Glide.with(this).load(it.data.avatar).placeholder(R.drawable.ic_avatarprofile).dontAnimate()
                    .into(imageViewUser)
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }

        mViewModel.isShowLoading.observe(this) { isShowLoading ->
            if (isShowLoading) showProgressDialog() else dismissProgressDialog()
        }

    }

    //set up edit date birth
    private fun setupEditTextViewDate() {
        textInputEditTextDate.addTextChangedListener(object : TextWatcher {
            private var current = ""
            private val ddmmyyyy = "DDMMYYYY"
            private val cal = Calendar.getInstance()

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString() != current) {
                    var clean = p0.toString().replace("[^\\d.]|\\.".toRegex(), "")
                    val cleanC = current.replace("[^\\d.]|\\.", "")

                    val cl = clean.length
                    var sel = cl
                    var i = 2
                    while (i <= cl && i < 6) {
                        sel++
                        i += 2
                    }
                    if (clean == cleanC) sel--

                    if (clean.length < 8) {
                        clean += ddmmyyyy.substring(clean.length)
                    } else {
                        var day = Integer.parseInt(clean.substring(0, 2))
                        var mon = Integer.parseInt(clean.substring(2, 4))
                        var year = Integer.parseInt(clean.substring(4, 8))

                        mon = if (mon < 1) 1 else if (mon > 12) 12 else mon
                        cal.set(Calendar.MONTH, mon - 1)
                        year = if (year < 1900) 1900 else if (year > 2200) 2200 else year
                        cal.set(Calendar.YEAR, year)

                        day = if (day > cal.getActualMaximum(Calendar.DATE)) cal.getActualMaximum(
                            Calendar.DATE
                        ) else day
                        clean = String.format("%02d%02d%02d", day, mon, year)
                    }

                    clean = String.format(
                        "%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8)
                    )

                    sel = if (sel < 0) 0 else sel
                    current = clean
                    textInputEditTextDate.setText(current)
                    textInputEditTextDate.setSelection(if (sel < current.count()) sel else current.count())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable) {

            }
        })
    }

    //set up select image local
    private fun setupSelectImageLocal() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, REQUEST_PERMISSION_IMAGE_GRALLERY)
            } else {
                pickImageFromGallery()
            }
        } else {
            pickImageFromGallery()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION_IMAGE_GRALLERY -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun pickImageFromGallery() {
        val build = AlertDialog.Builder(this)
        build.setTitle("Select Image")
        build.setMessage("Choose your option?")
        build.setPositiveButton("Gallery") { dialog, which ->
            dialog.dismiss()

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE_GRALLERY)
        }
        val dialog: AlertDialog = build.create()
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_GRALLERY && resultCode == Activity.RESULT_OK && data != null) {
            val uri: Uri = data.data!!
            mUri = uri

            Glide.with(this).load(mUri).placeholder(R.drawable.avatars).dontAnimate()
                .into(imageViewUser)
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private val REQUEST_IMAGE_GRALLERY = 132
        private val REQUEST_PERMISSION_IMAGE_GRALLERY = 142
    }

    override fun onCreatedDone() {

    }

}