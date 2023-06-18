package haken.dokemi.andoku.ui.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import haken.dokemi.andoku.base.BaseViewModel
import haken.dokemi.andoku.common.StorageService
import haken.dokemi.andoku.service.API
import haken.dokemi.andoku.service.models.dashboard.DashBoardResponse
import haken.dokemi.andoku.service.models.profile.myprofile.ProfileResponse
import haken.dokemi.andoku.service.models.profile.update.UpdateProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilesViewModel : BaseViewModel() {

    private val _isShowLoading = MutableLiveData<Boolean>().apply {
        value = false
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is Profile Fragment"
    }

    val text: LiveData<String> = _text

    var isShowLoading: LiveData<Boolean> = _isShowLoading

    //inform my profile
    private val _isShowUserProfile = MutableLiveData<ProfileResponse>()
    val showUserProfile: LiveData<ProfileResponse> = _isShowUserProfile

    private val _isShowUserUpdate = MutableLiveData<UpdateProfileResponse>()
    val showUserUpdate: LiveData<UpdateProfileResponse> = _isShowUserUpdate

    //truyen da luu
    private val _isDashBoardResponse = MutableLiveData<DashBoardResponse>()
    private val _isLoadFalse = MutableLiveData<Boolean>()
    val isLoadFalse: LiveData<Boolean> = _isLoadFalse
    val mDashBoardResponseData: LiveData<DashBoardResponse> = _isDashBoardResponse

    var token = ""

    fun callProfile() {
        val token = StorageService.instance?.token.toString()
        _isShowLoading.postValue(true)

        API.apiService.profile(token).enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                _isShowLoading.postValue(false)
                if (response.isSuccessful) {
                    Log.d("qqq", "sai")
                    _isShowUserProfile.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                _isShowLoading.postValue(false)
            }
        })
    }

    //truyen da luu
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

    fun updateProfile(
        nick_name: RequestBody,
        name: RequestBody,
        date_of_birth: RequestBody,
        email: RequestBody,
        avatar: MultipartBody.Part
    ) {
        val token = StorageService.instance?.token.toString()
        _isShowLoading.postValue(true)

        API.apiService.updateProfile(nick_name, name, date_of_birth, email, avatar, token)
            .enqueue(object : Callback<UpdateProfileResponse> {
                override fun onResponse(
                    call: Call<UpdateProfileResponse>,
                    response: Response<UpdateProfileResponse>
                ) {
                    _isShowLoading.postValue(false)
                    if (response.isSuccessful) {
                        _isShowUserUpdate.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                    _isShowLoading.postValue(false)
                }
            })
    }

    fun getUserUpdate2(
        nick_name: RequestBody,
        name: RequestBody,
        date_of_birth: RequestBody,
        email: RequestBody,
    ) {
        val token = StorageService.instance?.token.toString()
        _isShowLoading.postValue(true)

        API.apiService.updateProfile2(nick_name, name, date_of_birth, email, token)
            .enqueue(object : Callback<UpdateProfileResponse> {
                override fun onResponse(
                    call: Call<UpdateProfileResponse>,
                    response: Response<UpdateProfileResponse>
                ) {
                    _isShowLoading.postValue(false)
                    if (response.isSuccessful) {
                        _isShowUserUpdate.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                    _isShowLoading.postValue(false)
                }
            })
    }

}