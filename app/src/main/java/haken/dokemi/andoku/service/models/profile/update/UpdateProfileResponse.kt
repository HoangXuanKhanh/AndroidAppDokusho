package haken.dokemi.andoku.service.models.profile.update

data class UpdateProfileResponse(
    val code_status: Int,
    val `data`: UpdateProfileResponseData,
    val message: String
)