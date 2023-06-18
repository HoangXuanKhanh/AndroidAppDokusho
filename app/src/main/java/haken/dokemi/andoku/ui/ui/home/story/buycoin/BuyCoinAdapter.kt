package haken.dokemi.andoku.ui.ui.home.story.buycoin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import haken.dokemi.andoku.R
import haken.dokemi.andoku.service.models.coin.DataCoin
import com.bumptech.glide.Glide

class BuyCoinAdapter(

    private val dataList: List<DataCoin>,
    private val listener: OnClickListener

) : RecyclerView.Adapter<BuyCoinAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_buy_coin_custom_layout, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding(dataList[position])

        holder.itemView.setOnClickListener {
            listener.onClick(dataList[position])
        }
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textAmountCoin: TextView = itemView.findViewById(R.id.text_view_amount_coin)
        val textPrice: TextView = itemView.findViewById(R.id.text_view_price)
        val image_view_buy_coin: ImageView = itemView.findViewById(R.id.image_view_buy_coin)

        fun binding(mCoinResponseData: DataCoin) {
            textAmountCoin.text = mCoinResponseData.xu
            textPrice.text = mCoinResponseData.usd
            Glide.with(itemView.context).load(mCoinResponseData.image).centerCrop()
                .into(image_view_buy_coin)
        }

    }

    interface OnClickListener {
        fun onClick(idInapp: DataCoin)
    }

}