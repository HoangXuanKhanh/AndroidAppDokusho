package haken.dokemi.andoku.ui.ui.home.story.comment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import haken.dokemi.andoku.R
import haken.dokemi.andoku.base.BaseActivity

class CommentActivity : BaseActivity<CommentViewModel>() {

    lateinit var imageViewBack: ImageView
    lateinit var recyclerViewComment: RecyclerView
    lateinit var editTextDetailComment: EditText
    lateinit var imageViewSendComment: ImageView
    var id = 1

    override fun onCreateStart(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_comment)
        supportActionBar?.hide()
    }

    override fun setupViewComponent() {

    }

    override fun setupViewModel(): CommentViewModel {
        return ViewModelProvider(this)[CommentViewModel::class.java]
    }

    override fun setupView() {
        imageViewBack = findViewById(R.id.image_view_back)
        recyclerViewComment = findViewById(R.id.recycler_view_detail_comment)
        editTextDetailComment = findViewById(R.id.edit_text_detail_comment)
        imageViewSendComment = findViewById(R.id.image_view_send_comment)

        imageViewBack.setOnClickListener { finish() }

        id = intent.getIntExtra("ID", 1)
        mViewModel.callGetComment(id)

        imageViewSendComment.setOnClickListener {
            val strComment = editTextDetailComment.text.toString().trim()

            if (strComment.isNullOrEmpty()) {
                val mDialog = Dialog(this)
                mDialog.setCancelable(true)
                mDialog.setContentView(R.layout.custom_comment_layout)
                val mWindow = mDialog.window
                mWindow!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                mDialog.show()
                editTextDetailComment.requestFocus()

            } else {
                mViewModel.callPostDetailComment(id, strComment)
            }
        }
    }

    override fun setupCommonBinding() {

    }

    override fun setupBinding() {
        mViewModel.getCommentResponse.observe(this) {
            recyclerViewComment.apply {
                adapter = AdapterComment(it.data)
                layoutManager =
                    LinearLayoutManager(this@CommentActivity, LinearLayoutManager.VERTICAL, false)
            }
        }

        mViewModel.postCommentResponse.observe(this) {
            if (it.code_status == 1) {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
        }

        mViewModel.isShowLoading.observe(this) {
            if (it) showProgressDialog() else dismissProgressDialog()
        }
    }

    override fun onCreatedDone() {

    }
}