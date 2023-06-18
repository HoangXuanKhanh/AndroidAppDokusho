package haken.dokemi.andoku.service.models.profile.myprofile

data class ProfileResponse(
    val code_status: Int,
    val `data`: ProfileResponseData,
    val message: String
)