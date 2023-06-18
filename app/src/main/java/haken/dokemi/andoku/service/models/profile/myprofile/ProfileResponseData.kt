package haken.dokemi.andoku.service.models.profile.myprofile

data class ProfileResponseData(
    val avatar: String,
    val coin: Int,
    val date_of_birth: String,
    val email: String,
    val id: Int,
    val name: String,
    val nick_name: String
)