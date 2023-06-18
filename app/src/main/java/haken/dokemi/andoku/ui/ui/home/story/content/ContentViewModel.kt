package haken.dokemi.andoku.ui.ui.home.story.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import haken.dokemi.andoku.base.BaseViewModel
import haken.dokemi.andoku.common.StorageService
import haken.dokemi.andoku.service.API
import haken.dokemi.andoku.service.models.content.ContentResponse
import haken.dokemi.andoku.service.models.content.ContentResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContentViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is detail story Activity"
    }
    val text: LiveData<String> = _text

    private val _isShowLoading = MutableLiveData<Boolean>().apply {
        value = false
    }

    private val _contentResponse = MutableLiveData<List<ContentResponseData>>()
    val contentResponse: LiveData<List<ContentResponseData>> = _contentResponse

    fun callContent(isStory: Int, number: Int) {
        val token = StorageService.instance?.token.toString()

        _isShowLoading.postValue(true)
        API.apiService.content(isStory, number).enqueue(object : Callback<ContentResponse> {
            override fun onResponse(
                call: Call<ContentResponse>,
                response: Response<ContentResponse>
            ) {
                if (response.isSuccessful) {
                    _contentResponse.postValue(response.body()!!.data)
                }
            }

            override fun onFailure(call: Call<ContentResponse>, t: Throwable) {
            }
        })
    }


}