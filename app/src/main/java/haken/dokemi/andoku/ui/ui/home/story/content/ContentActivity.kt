package haken.dokemi.andoku.ui.ui.home.story.content

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import haken.dokemi.andoku.R
import haken.dokemi.andoku.base.BaseActivity
import haken.dokemi.andoku.service.models.content.ContentResponseData

class ContentActivity : BaseActivity<ContentViewModel>() {

    lateinit var textViewTitle: TextView
    lateinit var textViewContent: TextView
    lateinit var imageViewBack: ImageView

    override fun onCreateStart(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_content)
        supportActionBar?.hide()
    }

    override fun setupViewComponent() {

    }

    override fun setupViewModel(): ContentViewModel {
        return ViewModelProvider(this)[ContentViewModel::class.java]
    }

    override fun setupView() {
        textViewContent = findViewById(R.id.text_view_content)
        textViewTitle = findViewById(R.id.text_view_title)
        imageViewBack = findViewById(R.id.image_view_back)

        imageViewBack.setOnClickListener {
            finish()
        }

        val id = intent.getIntExtra("ID", 1)
        val number = intent.getIntExtra("NUMBER", 1)

        mViewModel.callContent(id, number)
    }

    override fun setupCommonBinding() {

    }

    override fun setupBinding() {
        mViewModel.contentResponse.observe(this) {
            setupDisplay(it[0])
        }
    }

    private fun setupDisplay(mContentResponseData: ContentResponseData) {
        textViewTitle.text = mContentResponseData.name_chapter

        textViewContent.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(mContentResponseData.content_chapter, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(mContentResponseData.content_chapter)
        }

    }

    override fun onCreatedDone() {

    }
}