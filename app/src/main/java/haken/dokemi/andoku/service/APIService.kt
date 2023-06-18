import haken.dokemi.andoku.service.models.chapter.ChapterResponse
import haken.dokemi.andoku.service.models.coin.CoinResponse
import haken.dokemi.andoku.service.models.comment.get.GetCommentResponse
import haken.dokemi.andoku.service.models.comment.post.PostCommentResponse
import haken.dokemi.andoku.service.models.content.ContentResponse
import haken.dokemi.andoku.service.models.dashboard.DashBoardResponse
import haken.dokemi.andoku.service.models.inform.InformResponse
import retrofit2.Call
import haken.dokemi.andoku.service.models.login.LoginResponse
import haken.dokemi.andoku.service.models.profile.myprofile.ProfileResponse
import haken.dokemi.andoku.service.models.profile.update.UpdateProfileResponse
import haken.dokemi.andoku.service.models.register.RegisterResponse
import haken.dokemi.andoku.service.models.savestory.SaveStoryResponse
import haken.dokemi.andoku.service.models.star.VoteStarResponse
import haken.dokemi.andoku.service.models.story.StoryResponse
import haken.dokemi.andoku.service.models.topic.TopicResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface APIService {

    @POST("v1/sign-in")
    fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<LoginResponse>

    @POST("v1/sign-up")
    fun register(
        @Query("name") name: String,
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<RegisterResponse>

    @GET("v1/get-topic")
    fun topic(
    ): Call<TopicResponse>

    @GET("v1/get-home-story")
    fun homeStory(
        @Query("topic_id") topic_id: Int
    ): Call<StoryResponse>

    @GET("v1/get-user-info")
    fun profile(
        @Header("Authorization") token: String?
    ): Call<ProfileResponse>

    @GET("v1/info-story")
    fun inform(
        @Query("story_id") story_id: Int,
        @Header("Authorization") token: String?
    ): Call<InformResponse>

    @GET("v1/get-list-chapter")
    fun chapter(
        @Query("story_id") story_id: Int
    ): Call<ChapterResponse>

    @GET("v1/get-content-chapter")
    fun content(
        @Query("story_id") story_id: Int,
        @Query("number") number: Int
    ): Call<ContentResponse>

    @GET("v1/get-save-story")
    fun dashboard(
        @Header("Authorization") token: String?
    ): Call<DashBoardResponse>

    @POST("v1/post-save-story")
    fun saveStory(
        @Query("story_id") story_id: Int,
        @Header("Authorization") token: String?
    ): Call<SaveStoryResponse>

    @POST("v1/post-vote-star")
    fun voteStar(
        @Query("story_id") story_id: Int,
        @Query("vote_star") vote_star: Int,
        @Header("Authorization") token: String?
    ): Call<VoteStarResponse>

    @POST("v1/post-user-comment")
    fun postComment(
        @Query("story_id") story_id: Int,
        @Query("comment") comment: String,
        @Header("Authorization") token: String?
    ): Call<PostCommentResponse>

    @GET("v1/get-user-comment")
    fun getComment(
        @Query("story_id") story_id: Int,
        @Header("Authorization") token: String?
    ): Call<GetCommentResponse>

    @Multipart
    @POST("v1/update-user-info")
    fun updateProfile(
        @Part("nick_name") nick_name: RequestBody,
        @Part("name") name: RequestBody,
        @Part("date_of_birth") date_of_birth: RequestBody,
        @Part("email") email: RequestBody,
        @Part avatar: MultipartBody.Part,
        @Header("Authorization") token: String?
    ): Call<UpdateProfileResponse>

    @Multipart
    @POST("v1/update-user-info")
    fun updateProfile2(
        @Part("nick_name") nick_name: RequestBody,
        @Part("name") name: RequestBody,
        @Part("date_of_birth") date_of_birth: RequestBody,
        @Part("email") email: RequestBody,
        @Header("Authorization") token: String?
    ): Call<UpdateProfileResponse>

    @GET("v1/get-coin")
    fun getCoin(
        @Header("Authorization") token: String?
    ): Call<CoinResponse>

}
