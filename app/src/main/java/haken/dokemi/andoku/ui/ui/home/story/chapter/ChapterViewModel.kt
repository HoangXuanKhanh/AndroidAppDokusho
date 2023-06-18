package haken.dokemi.andoku.ui.ui.home.story.chapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import haken.dokemi.andoku.base.BaseViewModel
import haken.dokemi.andoku.service.API
import haken.dokemi.andoku.service.models.chapter.ChapterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChapterViewModel: BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is detail story Activity"
    }
    val text: LiveData<String> = _text

    private val _isShowLoading = MutableLiveData<Boolean>().apply {
        value = false
    }

    private val _chapterResponse = MutableLiveData<ChapterResponse>()
    val chapterResponse: LiveData<ChapterResponse> = _chapterResponse

    fun callChapter(idStory: Int) {
        API.apiService.chapter(idStory).enqueue(object : Callback<ChapterResponse> {
            override fun onResponse(
                call: Call<ChapterResponse>,
                response: Response<ChapterResponse>
            ) {
                if (response.isSuccessful) {
                    _chapterResponse.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ChapterResponse>, t: Throwable) {

            }
        })
    }

}