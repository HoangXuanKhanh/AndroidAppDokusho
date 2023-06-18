package haken.dokemi.andoku.ui.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import androidx.core.util.Consumer
import haken.dokemi.andoku.R
import haken.dokemi.andoku.service.models.dashboard.DashBoardResponseData

//item_dash_board_layout
class AdapterDashBroad(
    private val dataList: List<DashBoardResponseData>,
    private val listener: Consumer<Int>

) : RecyclerView.Adapter<AdapterDashBroad.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dash_board_layout, parent, false)

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
        val imageViewSaveStory: ImageView = mView.findViewById(R.id.image_view_save_story)
        val textViewStory: TextView = mView.findViewById(R.id.text_view_story)
        val textViewAuthor: TextView = mView.findViewById(R.id.text_view_author)
        val ratingBarStory: RatingBar = mView.findViewById(R.id.rating_bar_story_book_mark)

        fun binding(mDashBoardResponseData: DashBoardResponseData) {
            Glide.with(mView.context).load(mDashBoardResponseData.story_image).centerCrop()
                .into(imageViewSaveStory)
            textViewStory.text = mDashBoardResponseData.story_name
            textViewAuthor.text = mDashBoardResponseData.story_name_author

            ratingBarStory.rating = mDashBoardResponseData.story_vote_star.toFloat()
        }
    }

}