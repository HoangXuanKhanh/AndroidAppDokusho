package haken.dokemi.andoku.ui.ui.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import haken.dokemi.andoku.R

class AdapterChooseColor(
    private val dataList: List<ColorModel>,
    private val mOnClickListener: OnClickListener,
): RecyclerView.Adapter<AdapterChooseColor.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_choose_color_layout, parent, false)

        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding(dataList[position])

        holder.itemView.setOnClickListener {
            mOnClickListener.onItemClick(dataList[position])
//            selectedItemPosition = position
            dataList[position].check = !dataList[position].check
            notifyDataSetChanged()
        }

        holder.itemView.isSelected = dataList[position].check
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(private val mView: View): RecyclerView.ViewHolder(mView){
        val textView: TextView = mView.findViewById(R.id.text_view)
        val imageView: ImageView = mView.findViewById(R.id.image_view)
        val cardView: RelativeLayout = mView.findViewById(R.id.card_view)

        fun binding(model: ColorModel) {
            textView.text = model.text
            imageView.setImageResource(model.image)
        }
    }

    interface OnClickListener {
        fun onItemClick(model: ColorModel)
    }

}