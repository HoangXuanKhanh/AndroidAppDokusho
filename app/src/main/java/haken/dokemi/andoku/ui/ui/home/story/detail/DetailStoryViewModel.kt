package haken.dokemi.andoku.ui.ui.home.story.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import haken.dokemi.andoku.base.BaseViewModel
import haken.dokemi.andoku.common.StorageService
import haken.dokemi.andoku.service.API
import haken.dokemi.andoku.service.models.comment.post.PostCommentResponse
import haken.dokemi.andoku.service.models.inform.InformResponse
import haken.dokemi.andoku.service.models.inform.InformResponseData
import haken.dokemi.andoku.service.models.savestory.SaveStoryResponse
import haken.dokemi.andoku.service.models.star.VoteStarResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailStoryViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is detail story Activity"
    }
    val text: LiveData<String> = _text

    private val _isShowLoading = MutableLiveData<Boolean>().apply {
        value = false
    }

    var isShowLoading: LiveData<Boolean> = _isShowLoading

    private val _informResponse = MutableLiveData<List<InformResponseData>>()
    val informResponse: LiveData<List<InformResponseData>> = _informResponse

    private val _isSaveStoryResponse = MutableLiveData<SaveStoryResponse>()
    val isSaveStoryResponse: LiveData<SaveStoryResponse> = _isSaveStoryResponse

    private val _voteStarResponse = MutableLiveData<VoteStarResponse>()
    val voteStarResponse: LiveData<VoteStarResponse> = _voteStarResponse

    private val _postCommentResponse = MutableLiveData<PostCommentResponse>()
    val postCommentResponse: LiveData<PostCommentResponse> = _postCommentResponse


    var token = ""

    fun callInform(idStory: Int) {
        val token = StorageService.instance?.token.toString()

        _isShowLoading.postValue(true)
        API.apiService.inform(idStory, token).enqueue(object : Callback<InformResponse> {
            override fun onResponse(
                call: Call<InformResponse>,
                response: Response<InformResponse>
            ) {
                if (response.isSuccessful) {
                    _informResponse.postValue(response.body()!!.data)
                }
            }

            override fun onFailure(call: Call<InformResponse>, t: Throwable) {
            }
        })
    }

    fun callSaveStory(idStory: Int) {
        val token = StorageService.instance?.token.toString()

        API.apiService.saveStory(idStory, token)
            .enqueue(object : Callback<SaveStoryResponse> {
                override fun onResponse(
                    call: Call<SaveStoryResponse>,
                    response: Response<SaveStoryResponse>
                ) {
                    if (response.isSuccessful) {
                        _isSaveStoryResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<SaveStoryResponse>, t: Throwable) {
                }

            })
    }

    fun callVoteStar(idStory: Int, vote_star: Int) {
        val token = StorageService.instance?.token.toString()

        API.apiService.voteStar(idStory, vote_star, token)
            .enqueue(object : Callback<VoteStarResponse> {
                override fun onResponse(
                    call: Call<VoteStarResponse>,
                    response: Response<VoteStarResponse>
                ) {
                    if (response.isSuccessful) {
                        _voteStarResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<VoteStarResponse>, t: Throwable) {

                }

            })
    }

    fun callPostComment(idStory: Int, comment: String) {
        val token = StorageService.instance?.token.toString()

        API.apiService.postComment(idStory, comment, token)
            .enqueue(object : Callback<PostCommentResponse> {
                override fun onResponse(
                    call: Call<PostCommentResponse>,
                    response: Response<PostCommentResponse>
                ) {
                    if (response.isSuccessful) {
                        _postCommentResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<PostCommentResponse>, t: Throwable) {

                }

            })
    }

}