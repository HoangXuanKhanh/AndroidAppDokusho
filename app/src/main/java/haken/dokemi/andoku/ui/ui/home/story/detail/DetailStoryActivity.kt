package haken.dokemi.andoku.ui.ui.home.story.detail

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import haken.dokemi.andoku.R
import haken.dokemi.andoku.base.BaseActivity
import haken.dokemi.andoku.service.models.inform.InformResponseCategory
import haken.dokemi.andoku.service.models.inform.InformResponseData
import haken.dokemi.andoku.ui.ui.home.story.chapter.ChapterActivity
import com.bumptech.glide.Glide
import haken.dokemi.andoku.ui.ui.home.story.comment.CommentActivity
import me.zhanghai.android.materialratingbar.MaterialRatingBar

class DetailStoryActivity : BaseActivity<DetailStoryViewModel>() {

    lateinit var imageViewBack: ImageView
    lateinit var textViewNameStory: TextView
    lateinit var imageViewStory: ImageView
    lateinit var textViewEye: TextView
    lateinit var textViewStar: TextView
    lateinit var textViewComment: TextView
    lateinit var textViewAuthor: TextView
    lateinit var textViewCategory: TextView
    lateinit var textViewResource: TextView
    lateinit var textViewEdit: TextView
    lateinit var textViewStatus: TextView
    lateinit var textViewContent: TextView
    lateinit var buttonReads: Button
    lateinit var buttonSaveStory: Button
    lateinit var editTextComment: EditText
    lateinit var imageViewSendComment: ImageView
    lateinit var imageViewIconComment: ImageView
    lateinit var ratingBarInform: MaterialRatingBar

    override fun onCreateStart(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_detail_story)

        supportActionBar?.hide()
    }

    override fun setupViewComponent() {

    }

    override fun setupViewModel(): DetailStoryViewModel {
        return ViewModelProvider(this)[DetailStoryViewModel::class.java]
    }

    override fun setupView() {
        imageViewBack = findViewById(R.id.image_detail_story_back)
        textViewNameStory = findViewById(R.id.text_view_name_story)
        imageViewStory = findViewById(R.id.image_view_story)
        textViewEye = findViewById(R.id.text_view_eye)
        textViewStar = findViewById(R.id.text_view_star)
        textViewComment = findViewById(R.id.text_view_comment)
        textViewAuthor = findViewById(R.id.text_view_author)
        textViewCategory = findViewById(R.id.text_view_category)
        textViewResource = findViewById(R.id.text_view_resource)
        textViewEdit = findViewById(R.id.text_view_edit)
        textViewStatus = findViewById(R.id.text_view_status)
        textViewContent = findViewById(R.id.text_view_content)
        buttonReads = findViewById(R.id.button_reading)
        buttonSaveStory = findViewById(R.id.button_save_library)
        editTextComment = findViewById(R.id.edit_text_comment)
        imageViewSendComment = findViewById(R.id.image_view_send_comment)
        imageViewIconComment = findViewById(R.id.image_view_icon_comment)
        ratingBarInform = findViewById(R.id.rating_bar_inform)

        val id = intent.getIntExtra("ID", 1)

        imageViewBack.setOnClickListener {
            finish()
        }

        mViewModel.callInform(id)

        buttonSaveStory.setOnClickListener {
            mViewModel.callSaveStory(id)
//            buttonSaveStory.setBackgroundResource(R.drawable.bgr_view_success)
        }

        ratingBarInform.setOnRatingChangeListener { ratingBar, rating ->
            mViewModel.callVoteStar(id, rating.toInt())
            Toast.makeText(this, "Vote Star: $rating", Toast.LENGTH_LONG).show()
        }

        imageViewSendComment.setOnClickListener {
            val strComment = editTextComment.text.toString().trim()

            if (strComment.isNullOrEmpty()) {
                val mDialog = Dialog(this)
                mDialog.setCancelable(true)
                mDialog.setContentView(R.layout.custom_comment_layout)
                val mWindow = mDialog.window
                mWindow!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                mDialog.show()
                editTextComment.requestFocus()

            } else {
                mViewModel.callPostComment(id, strComment)
            }
        }

    }

    override fun setupCommonBinding() {

    }

    override fun setupBinding() {
        mViewModel.informResponse.observe(this) { informResponse ->
            setupDisplay(informResponse[0])
        }

        mViewModel.isSaveStoryResponse.observe(this) {
            if (it.status == 1) {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
        }
//
        mViewModel.postCommentResponse.observe(this) {
            if (it.code_status == 1) {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
        }

        mViewModel.voteStarResponse.observe(this) {
            if (it.status == 1) {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
        }

//        mViewModel.isShowLoading.observe(this) {
//            if (it) showProgressDialog() else dismissProgressDialog()
//        }

    }

    @SuppressLint("SetTextI18n")
    private fun setupDisplay(mInformResponseData: InformResponseData) {
        textViewNameStory.text = mInformResponseData.story_name
        Glide.with(applicationContext).load(mInformResponseData.story_image).centerCrop()
            .into(imageViewStory)
        textViewStar.text = mInformResponseData.story_vote_star
        textViewComment.text = mInformResponseData.number_comment
        textViewAuthor.text = mInformResponseData.story_name_author
        textViewResource.text = mInformResponseData.source_story
        textViewEdit.text = mInformResponseData.edit
        textViewStatus.text = mInformResponseData.status
        textViewContent.text = mInformResponseData.content_story
//        textViewEye.text = mInformResponseData

        for (mData: InformResponseCategory in mInformResponseData.category) {
            textViewCategory.text = mData.category_name
        }


        buttonReads.setOnClickListener {
            val intent = Intent(this, ChapterActivity::class.java)

            intent.putExtra("ID", mInformResponseData.id)
            startActivity(intent)
//            buttonReads.setBackgroundResource(R.drawable.bgr_view_success)
        }

        imageViewIconComment.setOnClickListener {
            val intent = Intent(this, CommentActivity::class.java)

            intent.putExtra("ID", mInformResponseData.id)
            startActivity(intent)
        }


    }

    override fun onCreatedDone() {

    }

}