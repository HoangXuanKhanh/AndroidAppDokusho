package haken.dokemi.andoku.ui.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import haken.dokemi.andoku.common.StorageService
import haken.dokemi.andoku.service.API
import haken.dokemi.andoku.service.models.dashboard.DashBoardResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private val _isShowLoading = MutableLiveData<Boolean>().apply {
        value = false
    }

    var isShowLoading: LiveData<Boolean> = _isShowLoading

    private val _isDashBoardResponse = MutableLiveData<DashBoardResponse>()
    private val _isLoadFalse = MutableLiveData<Boolean>()
    val isLoadFalse: LiveData<Boolean> = _isLoadFalse
    val isDashBoardResponse: LiveData<DashBoardResponse> = _isDashBoardResponse

    var token = ""

    fun dashboard() {
        val token = StorageService.instance?.token.toString()
        API.apiService.dashboard(token).enqueue(object : Callback<DashBoardResponse> {
            override fun onResponse(
                call: Call<DashBoardResponse>,
                response: Response<DashBoardResponse>
            ) {
                if (response.isSuccessful) {
                    _isDashBoardResponse.postValue(response.body())
                    _isLoadFalse.postValue(true)
                    return
                }
                _isLoadFalse.postValue(false)
            }

            override fun onFailure(call: Call<DashBoardResponse>, t: Throwable) {
            }

        })
    }

}