package haken.dokemi.andoku.ui.ui.home.story.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import haken.dokemi.andoku.base.BaseViewModel
import haken.dokemi.andoku.common.StorageService
import haken.dokemi.andoku.service.API
import haken.dokemi.andoku.service.models.comment.get.GetCommentResponse
import haken.dokemi.andoku.service.models.comment.post.PostCommentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentViewModel : BaseViewModel() {

    private val _isShowLoading = MutableLiveData<Boolean>().apply {
        value = false
    }

    var isShowLoading: LiveData<Boolean> = _isShowLoading

    private val _getCommentResponse = MutableLiveData<GetCommentResponse>()
    val getCommentResponse: LiveData<GetCommentResponse> = _getCommentResponse

    private val _postCommentResponse = MutableLiveData<PostCommentResponse>()
    val postCommentResponse: LiveData<PostCommentResponse> = _postCommentResponse

    var token = ""

    fun callGetComment(idStory: Int) {
        val token = StorageService.instance?.token.toString()
        _isShowLoading.postValue(true)
        API.apiService.getComment(idStory, token).enqueue(object : Callback<GetCommentResponse> {
            override fun onResponse(
                call: Call<GetCommentResponse>,
                response: Response<GetCommentResponse>
            ) {
                _isShowLoading.postValue(false)
                if (response.isSuccessful) {
                    _getCommentResponse.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<GetCommentResponse>, t: Throwable) {
                _isShowLoading.postValue(false)
            }

        })
    }

    fun callPostDetailComment(idStory: Int, comment: String){
        val token = StorageService.instance?.token.toString()

        API.apiService.postComment(idStory, comment, token).enqueue(object : Callback<PostCommentResponse>{
            override fun onResponse(
                call: Call<PostCommentResponse>,
                response: Response<PostCommentResponse>
            ) {
                if (response.isSuccessful){
                    _postCommentResponse.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<PostCommentResponse>, t: Throwable) {

            }

        })
    }


}