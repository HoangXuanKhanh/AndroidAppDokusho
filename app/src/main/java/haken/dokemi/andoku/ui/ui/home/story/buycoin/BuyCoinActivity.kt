package haken.dokemi.andoku.ui.ui.home.story.buycoin

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import haken.dokemi.andoku.R
import haken.dokemi.andoku.base.BaseActivity
import haken.dokemi.andoku.base.currentMoney
import haken.dokemi.andoku.service.models.coin.DataCoin
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.PurchaseInfo

class BuyCoinActivity : BaseActivity<BuyCoinViewModel>(), BillingProcessor.IBillingHandler,
    BuyCoinAdapter.OnClickListener {

    lateinit var imageBuyCoinBack: ImageView
    lateinit var recyclerviewBuyCoin: RecyclerView
    lateinit var textViewTermPolicy: TextView
    lateinit var text_view_coin: TextView

    private val mDataList: ArrayList<DataCoin> = arrayListOf()
    lateinit var mBuyCoinAdapter: BuyCoinAdapter

    var bp: BillingProcessor? = null
    private val listIdInApp: ArrayList<String> = ArrayList()

    override fun onCreateStart(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_buy_coin)
        supportActionBar?.hide()
    }

    override fun setupViewComponent() {

    }

    override fun setupViewModel(): BuyCoinViewModel {
        return ViewModelProvider(this)[BuyCoinViewModel::class.java]
    }

    override fun setupView() {
        imageBuyCoinBack = findViewById(R.id.image_buy_coin_back)
        recyclerviewBuyCoin = findViewById(R.id.recycler_view_buy_coin)
        textViewTermPolicy = findViewById(R.id.text_view_term_policy)
        text_view_coin = findViewById(R.id.text_view_coin)

        imageBuyCoinBack.setOnClickListener { finish() }

        textViewTermPolicy.paint.isUnderlineText = true
        textViewTermPolicy.setOnClickListener {
//            val url = ""
//            val i = Intent(Intent.ACTION_VIEW)
//            i.data = Uri.parse(url)
//            startActivity(i)
        }

        bp = BillingProcessor(this, "", this)
        bp!!.initialize()

        mViewModel.getCoin()
    }

    override fun setupCommonBinding() {

    }

    override fun setupBinding() {
        mViewModel.coinResponse.observe(this) {
            text_view_coin.text = "${currentMoney(it.data.coin.toDouble())} ${"Xu"}"


        }
        recyclerviewBuyCoin.apply {
            adapter = BuyCoinAdapter(mDataList, this@BuyCoinActivity)
            layoutManager =
                GridLayoutManager(this@BuyCoinActivity, 2, GridLayoutManager.VERTICAL, false)
        }

        getListDataCoin()
    }

    private fun getListDataCoin() {
        mDataList.add(DataCoin(1, R.drawable.ic_coin, "500 xu", "$ 0,99", "five_intu_1"))
        mDataList.add(DataCoin(2, R.drawable.ic_coin, "4500 xu", "$ 49", "five_inte_2"))
        mDataList.add(DataCoin(3, R.drawable.ic_coin, "7000 xu", "$ 99", "five_into_3"))
        mDataList.add(DataCoin(4, R.drawable.ic_coin, "10000 xu", "$ 100", "five_inta_4"))
    }

    override fun onCreatedDone() {

    }

    override fun onProductPurchased(productId: String, details: PurchaseInfo?) {
        bp!!.consumePurchaseAsync(productId, object : BillingProcessor.IPurchasesResponseListener {
            override fun onPurchasesSuccess() {
                Toast.makeText(this@BuyCoinActivity, "Continue", Toast.LENGTH_SHORT).show()
            }

            override fun onPurchasesError() {
                Toast.makeText(this@BuyCoinActivity, "Error payment", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onPurchaseHistoryRestored() {

    }

    override fun onBillingError(errorCode: Int, error: Throwable?) {

    }

    override fun onBillingInitialized() {

    }

    override fun onClick(id_inapp: DataCoin) {
        bp!!.purchase(this, id_inapp.in_app)
    }

    override fun onDestroy() {
        if (bp != null) {
            bp!!.release()
        }
        super.onDestroy()
    }

}