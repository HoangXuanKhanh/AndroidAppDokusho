package haken.dokemi.andoku.ui.ui.home.story.buycoin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import haken.dokemi.andoku.base.BaseViewModel
import haken.dokemi.andoku.common.StorageService
import haken.dokemi.andoku.service.API
import haken.dokemi.andoku.service.models.coin.CoinResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuyCoinViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is buy coin Activity"
    }
    val text: LiveData<String> = _text

    private val _coinResponse = MutableLiveData<CoinResponse>()
    val coinResponse: LiveData<CoinResponse> = _coinResponse

    var token = ""

    fun getCoin() {
        val token = StorageService.instance?.token.toString()

        API.apiService.getCoin(token).enqueue(object : Callback<CoinResponse> {
            override fun onResponse(call: Call<CoinResponse>, response: Response<CoinResponse>) {
                if (response.isSuccessful) {
                    _coinResponse.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<CoinResponse>, t: Throwable) {

            }

        })
    }

}