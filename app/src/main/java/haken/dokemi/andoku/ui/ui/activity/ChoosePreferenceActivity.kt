package haken.dokemi.andoku.ui.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import haken.dokemi.andoku.R
import haken.dokemi.andoku.ui.MainHomeActivity

class ChoosePreferenceActivity : AppCompatActivity(), AdapterChooseColor.OnClickListener {

    lateinit var recyclerViewChoose: RecyclerView
    private var dataList = mutableListOf<ColorModel>()
    private val data = mutableListOf<ColorModel>()
    private var mAdapterChooseColor: AdapterChooseColor? = null
    lateinit var textViewSkip: TextView
    lateinit var textViewSave: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_preference)

        supportActionBar?.hide()
        initView()
    }

    private fun initView() {
        recyclerViewChoose = findViewById(R.id.recycler_view_choose)
        textViewSkip = findViewById(R.id.text_view_skip)
        textViewSave = findViewById(R.id.text_view_save)

        textViewSkip.setOnClickListener {
            startActivity(Intent(this, MainHomeActivity::class.java))
            finish()
        }

        textViewSave.setOnClickListener {
            startActivity(Intent(this, MainHomeActivity::class.java))
            finish()
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        mAdapterChooseColor = AdapterChooseColor(dataList, this)
        recyclerViewChoose.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(this, 2)
        recyclerViewChoose.adapter = mAdapterChooseColor
        recyclerViewChoose.layoutManager = layoutManager

        setUpListView()
    }

    private fun setUpListView() {
        dataList.add(ColorModel(R.drawable.ic_vientuong, "Viễn tưởng"))
        dataList.add(ColorModel(R.drawable.ic_hai, "Hài hước"))
        dataList.add(ColorModel(R.drawable.ic_action, "Hành động"))
        dataList.add(ColorModel(R.drawable.ic_kinhdi, "Kinh dị"))
        dataList.add(ColorModel(R.drawable.ic_ma, "Ma"))
        dataList.add(ColorModel(R.drawable.ic_thanthoai, "Thần thoại"))
        dataList.add(ColorModel(R.drawable.ic_ngontinh, "Ngôn tình"))
        dataList.add(ColorModel(R.drawable.ic_trinhtham, "Trinh thám"))
        dataList.add(ColorModel(R.drawable.ic_novel, "Tiểu thuyết"))
        dataList.add(ColorModel(R.drawable.ic_shortstory, "Truyện ngắn"))

    }

    override fun onItemClick(model: ColorModel) {
        if (model.check) {
            data.add(model)
        } else {
            data.remove(model)
        }
    }

}

data class ColorModel(
    val image: Int,
    val text: String,
    var check: Boolean = false

)