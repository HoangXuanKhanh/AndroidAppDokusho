package haken.dokemi.andoku.ui.ui.home.story.chapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.util.Consumer
import androidx.recyclerview.widget.RecyclerView
import haken.dokemi.andoku.R
import haken.dokemi.andoku.service.models.chapter.ChapterResponseData

class AdapterChapter(
    private val dataList: List<ChapterResponseData>,
    private val listener: Consumer<Int>

) : RecyclerView.Adapter<AdapterChapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.item_chapter_custom, parent, false)

        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding(dataList[position])

        holder.itemView.setOnClickListener {
            listener.accept(dataList[position].number)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {
        val imageView: ImageView = mView.findViewById(R.id.image_view)
        val textView: TextView = mView.findViewById(R.id.text_view)

        fun binding(mChapterResponseData: ChapterResponseData) {
            textView.text = mChapterResponseData.name_chapter
        }
    }

//    interface OnClickListener {
//        fun onClick(mChapterResponseData: ChapterResponseData)
//    }

}