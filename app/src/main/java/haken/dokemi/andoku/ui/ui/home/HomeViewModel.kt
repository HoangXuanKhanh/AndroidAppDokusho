package haken.dokemi.andoku.ui.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import haken.dokemi.andoku.service.API
import haken.dokemi.andoku.service.models.story.StoryResponse
import haken.dokemi.andoku.service.models.topic.TopicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    val text: LiveData<String> = _text

    private val _isShowLoading = MutableLiveData<Boolean>().apply {
        value = false
    }

    var isShowLoading: LiveData<Boolean> = _isShowLoading

    private val _topicResponse = MutableLiveData<TopicResponse>()
    val topicResponse: LiveData<TopicResponse> = _topicResponse

    private val _storyResponse = MutableLiveData<StoryResponse>()
    val storyResponse: LiveData<StoryResponse> = _storyResponse

    fun callTopic() {
        _isShowLoading.postValue(true)
        API.apiService.topic().enqueue(object : Callback<TopicResponse> {
            override fun onResponse(
                call: Call<TopicResponse>,
                response: Response<TopicResponse>
            ) {
                _isShowLoading.postValue(false)
                if (response.isSuccessful) {
                    _topicResponse.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<TopicResponse>, t: Throwable) {
                _isShowLoading.postValue(false)
            }
        })
    }

    fun callHomeStory(topic_id: Int) {
        _isShowLoading.postValue(true)
        API.apiService.homeStory(topic_id).enqueue(object : Callback<StoryResponse> {
            override fun onResponse(
                call: Call<StoryResponse>,
                response: Response<StoryResponse>
            ) {
                _isShowLoading.postValue(false)
                if (response.isSuccessful) {
                    _storyResponse.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                _isShowLoading.postValue(false)
            }
        })
    }

}