package haken.dokemi.andoku.ui.ui.home.story.comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import haken.dokemi.andoku.R
import haken.dokemi.andoku.service.models.comment.get.GetCommentResponseData

class AdapterComment(
    private val dataList: List<GetCommentResponseData>

): RecyclerView.Adapter<AdapterComment.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_comment_layout, parent, false)

        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(private val mView: View): RecyclerView.ViewHolder(mView){
        val textViewUserComment: TextView = mView.findViewById(R.id.text_view_user_comment)
//        val textViewDateComment: TextView = mView.findViewById(R.id.text_view_date_comment)
        val textViewContentComment: TextView = mView.findViewById(R.id.text_view_content_comment)

        fun binding(mGetCommentResponseData: GetCommentResponseData){
            textViewUserComment.text = mGetCommentResponseData.user_name
            textViewContentComment.text = mGetCommentResponseData.comment
//            textViewDateComment.text = mGetCommentResponseData.updated_at
//
//            val dateFormat = SimpleDateFormat("EEE, d, MMM")
//            val timeFormat = SimpleDateFormat("hh:mm a")
//            val givenFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//
//            val date = givenFormat.parse(mGetCommentResponseData.updated_at)
//            textViewDateComment.text = dateFormat.format(date)

        }
    }
}