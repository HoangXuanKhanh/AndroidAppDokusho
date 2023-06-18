package haken.dokemi.andoku.ui.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import haken.dokemi.andoku.R
import haken.dokemi.andoku.service.models.topic.TopicResponseData
import com.bumptech.glide.Glide

class AdapterHomeTopicFragment(

    private val dataTopic: List<TopicResponseData>,
    private val listener: OnClickLister

) : RecyclerView.Adapter<AdapterHomeTopicFragment.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_home_topic_layout, parent, false)

        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding(dataTopic[position])

        holder.itemView.setOnClickListener {
            listener.onClick(dataTopic[position])
        }
    }

    override fun getItemCount(): Int {
        return dataTopic.size
    }

    inner class MyViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {
        val image_view_topic: ImageView = mView.findViewById(R.id.image_view_topic)
        val text_view_topic: TextView = mView.findViewById(R.id.text_view_topic)

        fun binding(mTopicResponseData: TopicResponseData) {
            text_view_topic.text = mTopicResponseData.name_type
            Glide.with(mView.context).load(mTopicResponseData.icon).centerCrop()
                .into(image_view_topic)
        }

    }

    interface OnClickLister {
        fun onClick(mTopicResponseData: TopicResponseData)
    }

}