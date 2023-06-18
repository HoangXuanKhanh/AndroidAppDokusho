package haken.dokemi.andoku.service.models.coin

data class CoinResponse(
    val code_status: Int,
    val `data`: CoinResponseData,
    val message: String
)