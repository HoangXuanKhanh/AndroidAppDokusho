package haken.dokemi.andoku.ui.ui.profile

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import haken.dokemi.andoku.R
import haken.dokemi.andoku.base.BaseActivity
import haken.dokemi.andoku.base.currentMoney
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class MyProfileActivity : BaseActivity<ProfilesViewModel>() {

    private val SECOND_ACTIVITY_REQUEST_CODE_USER = 1999

    lateinit var textViewEditUserInform: TextView
    lateinit var textViewName: TextView
    lateinit var textViewDateBirth: TextView
    lateinit var textViewEmail: TextView
    lateinit var textViewNickName: TextView
    lateinit var textViewCoin: TextView
    lateinit var imageViewBack: ImageView
    lateinit var imageViewUser: CircleImageView

    lateinit var fragment: Fragment

    var mUri: String? = null

    override fun onCreateStart(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_profile_my)
        supportActionBar?.hide()
    }

    override fun setupViewComponent() {

    }

    override fun setupViewModel(): ProfilesViewModel {
        return ViewModelProvider(this)[ProfilesViewModel::class.java]
    }

    override fun setupView() {
        textViewEditUserInform = findViewById(R.id.text_view_edit_profile_inform)
        imageViewBack = findViewById(R.id.image_inform_profile_back)
        textViewName = findViewById(R.id.text_view_name)
        textViewDateBirth = findViewById(R.id.text_view_date_birth)
        textViewEmail = findViewById(R.id.text_view_email)
        textViewNickName = findViewById(R.id.text_view_nick_name)
        imageViewUser = findViewById(R.id.image_view_user_name)
        textViewCoin = findViewById(R.id.text_view_coin_inform)

        imageViewBack.setOnClickListener {
            finish()
        }

        mViewModel.callProfile()

        textViewEditUserInform.setOnClickListener {
            val intent = Intent(this, ProfileEditActivity::class.java)

            startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE_USER)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE_USER) {
            if (resultCode == RESULT_OK) {
                val returnNickName = data!!.getStringExtra("NICKNAMED")
                val returnName = data.getStringExtra("NAMED")
                val returnDATE = data.getStringExtra("DATED")
                val returnEmail = data.getStringExtra("EMAILED")

                textViewNickName.text = returnNickName
                textViewName.text = returnName
                textViewDateBirth.text = returnDATE
                textViewEmail.text = returnEmail

                mUri = data.getStringExtra("AVATARED")
                if (mUri != null) {
                    Glide.with(this).load(mUri).into(imageViewUser)
                }
            }
        }

    }

    override fun setupCommonBinding() {

    }

    override fun setupBinding() {
        mViewModel.showUserProfile.observe(this) {
            if (it.code_status == 1) {
                textViewNickName.text = it.data.nick_name
                textViewName.text = it.data.name
                textViewDateBirth.text = it.data.date_of_birth
                textViewEmail.text = it.data.email
                textViewCoin.text = "${currentMoney(it.data.coin.toDouble())} ${"Xu"}"
                Glide.with(this).load(it.data.avatar).into(imageViewUser)

            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }

        mViewModel.isShowLoading.observe(this) {
            if (it) showProgressDialog() else dismissProgressDialog()
        }

    }

    override fun onCreatedDone() {

    }


}