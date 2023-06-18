package haken.dokemi.andoku.service.models.login

data class LoginResponse(
    val code_status: Int,
    val message: String,
    val token: String
)