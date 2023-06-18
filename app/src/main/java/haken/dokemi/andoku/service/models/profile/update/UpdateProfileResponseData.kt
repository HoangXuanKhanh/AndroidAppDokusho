package haken.dokemi.andoku.service.models.profile.update

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponseData(

    @SerializedName("avatar")
    val avatar: String,
    val coin: Int,
    val date_of_birth: String,
    val email: String,
    val id: Int,
    val name: String,
    val nick_name: String

)