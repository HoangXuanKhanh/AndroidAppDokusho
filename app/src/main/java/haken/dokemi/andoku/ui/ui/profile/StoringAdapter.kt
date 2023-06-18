package haken.dokemi.andoku.ui.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.util.Consumer
import androidx.recyclerview.widget.RecyclerView
import haken.dokemi.andoku.R
import haken.dokemi.andoku.service.models.dashboard.DashBoardResponseData
import com.bumptech.glide.Glide

class StoringAdapter(
    private val dataList: List<DashBoardResponseData>,
    private val listener: Consumer<Int>

) : RecyclerView.Adapter<StoringAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoringAdapter.MyViewHolder {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_storing_profile, parent, false)

        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: StoringAdapter.MyViewHolder, position: Int) {
        holder.binding(dataList[position])

        holder.itemView.setOnClickListener {
            listener.accept(dataList[position].id)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {
        val text_view_story: TextView = mView.findViewById(R.id.text_view_story)
        val text_view_author: TextView = mView.findViewById(R.id.text_view_author)
        val imageViewSaveStory: ImageView = mView.findViewById(R.id.image_view_save_story)
        val ratingBarStory: RatingBar = mView.findViewById(R.id.rating_bar_story_book_mark)

        fun binding(mDashBoardResponseData: DashBoardResponseData) {
            Glide.with(mView.context).load(mDashBoardResponseData.story_image).centerCrop()
                .into(imageViewSaveStory)
            text_view_story.text = mDashBoardResponseData.story_name
            text_view_author.text = mDashBoardResponseData.story_name_author

            ratingBarStory.rating = mDashBoardResponseData.story_vote_star.toFloat()
        }

    }

}