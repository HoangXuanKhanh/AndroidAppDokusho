package haken.dokemi.andoku.ui.ui.home.story.chapter

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.core.util.Consumer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import haken.dokemi.andoku.R
import haken.dokemi.andoku.base.BaseActivity
import haken.dokemi.andoku.ui.ui.home.story.chapter.CartFragment.Companion.TAG
import haken.dokemi.andoku.ui.ui.home.story.content.ContentActivity

class ChapterActivity : BaseActivity<ChapterViewModel>(), Consumer<Int> {

    lateinit var imageViewBack: ImageView
    lateinit var recyclerViewChapter: RecyclerView
    lateinit var buttonBuy: Button
    var id = 1
    var isShowVideoAds: Boolean = true

    override fun onCreateStart(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_chapter)
        supportActionBar?.hide()
    }

    override fun setupViewComponent() {

    }

    override fun setupViewModel(): ChapterViewModel {
        return ViewModelProvider(this)[ChapterViewModel::class.java]
    }

    override fun setupView() {
        imageViewBack = findViewById(R.id.image_view_back)
        recyclerViewChapter = findViewById(R.id.recycler_view_chapter)
        buttonBuy = findViewById(R.id.button_buy)
        id = intent.getIntExtra("ID", 1)
        mViewModel.callChapter(id)
    }

    override fun setupCommonBinding() {

    }

    override fun setupBinding() {
        mViewModel.chapterResponse.observe(this) { chapterResponse ->
            recyclerViewChapter.apply {
                adapter = AdapterChapter(chapterResponse.data, this@ChapterActivity)
                layoutManager = LinearLayoutManager(this@ChapterActivity)
            }
        }

        imageViewBack.setOnClickListener {
            finish()
        }

        buttonBuy.setOnClickListener {
            if (isShowVideoAds){
                // show ads
                setUpViewOption()
            }else{
                buttonBuy.text = "Đã mở khoá toàn bộ chương"
                buttonBuy.isEnabled = true
            }

        }

    }

    private fun setUpViewOption() {
//        val mDialog = Dialog(this)
//        mDialog.setCancelable(true)
//        mDialog.setContentView(R.layout.custom_buy_chapter_layout)
//        val mWindow = mDialog.window
//        mWindow!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        mDialog.show()
        CartFragment().show(supportFragmentManager, TAG)
    }

    override fun onCreatedDone() {

    }

    override fun accept(position: Int) {
        when (position) {
            position -> {
                val intent = Intent(this, ContentActivity::class.java)

                intent.putExtra("ID", id)
                intent.putExtra("NUMBER", position)

                startActivity(intent)
            }
//            else -> {
//
//                CartFragment().show(supportFragmentManager, TAG)
//            }

        }
    }

}