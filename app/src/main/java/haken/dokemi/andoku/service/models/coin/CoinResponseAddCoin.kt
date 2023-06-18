package haken.dokemi.andoku.service.models.coin

data class CoinResponseAddCoin(
    val USD: String,
    val id: Int,
    val xu: Int,
    var isBuy: Boolean = false,
    var inapp_id: String = ""
)