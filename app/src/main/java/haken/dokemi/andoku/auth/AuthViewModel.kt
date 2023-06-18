import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import haken.dokemi.andoku.base.BaseViewModel
import haken.dokemi.andoku.service.API
import haken.dokemi.andoku.service.models.login.LoginResponse
import haken.dokemi.andoku.service.models.register.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _loginResponseData = MutableLiveData<LoginResponse>()
    var loginResponseData: LiveData<LoginResponse> = _loginResponseData

    private val _registerResponseData = MutableLiveData<RegisterResponse>()
    var registerResponseData: LiveData<RegisterResponse> = _registerResponseData

    private val _isShowLoading = MutableLiveData<Boolean>().apply {
        value = false
    }

    var isShowLoading: LiveData<Boolean> = _isShowLoading

    fun callLoginAccount(email: String, password: String) {
        _isShowLoading.postValue(true)
        API.apiService.login(email, password).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isShowLoading.postValue(false)
                if (response.isSuccessful) {
                    _loginResponseData.postValue(response.body()!!)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isShowLoading.postValue(false)
            }

        })
    }

    fun callRegister(name: String, email: String, password: String) {
        _isShowLoading.postValue(false)
        API.apiService.register(name, email, password).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isShowLoading.postValue(false)
                if (response.isSuccessful) {
                    _registerResponseData.postValue(response.body()!!)
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isShowLoading.postValue(false)
            }

        })
    }

}