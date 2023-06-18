package haken.dokemi.andoku.service.models.coin

data class CoinResponseData(
    val add_coin: List<CoinResponseAddCoin>,
    val coin: Int,
    val user_id: Int
)