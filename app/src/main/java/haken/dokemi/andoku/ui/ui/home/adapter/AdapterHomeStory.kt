package haken.dokemi.andoku.ui.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.util.Consumer
import androidx.recyclerview.widget.RecyclerView
import haken.dokemi.andoku.R
import haken.dokemi.andoku.service.models.story.StoryResponseData
import com.bumptech.glide.Glide

class AdapterHomeStory(
    private val dataList: List<StoryResponseData>,
    private val listener: Consumer<Int>

) : RecyclerView.Adapter<AdapterHomeStory.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_home_story_layout, parent, false)

        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding(dataList[position])

        holder.itemView.setOnClickListener {
            listener.accept(dataList[position].id)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {
        val imageView: ImageView = mView.findViewById(R.id.image_view)
        val textViewName: TextView = mView.findViewById(R.id.text_view_name)
        val textViewAuthor: TextView = mView.findViewById(R.id.text_view_author)
        val ratingBar: RatingBar = mView.findViewById(R.id.rating_bar_story)

        fun binding(mStoryResponseData: StoryResponseData) {
            Glide.with(mView.context).load(mStoryResponseData.story_image).centerCrop()
                .into(imageView)
            textViewName.text = mStoryResponseData.story_name
            textViewAuthor.text = mStoryResponseData.story_name_author
            ratingBar.rating = mStoryResponseData.story_vote_star.toFloat()
//            ratingBar.numStars = mComicResponseData.story_vote_star.toInt()
        }
    }

}