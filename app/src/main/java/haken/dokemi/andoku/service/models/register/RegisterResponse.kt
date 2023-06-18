package haken.dokemi.andoku.service.models.register

data class RegisterResponse(
    val code_status: Int,
    val `data`: RegisterResponseData,
    val message: String
)