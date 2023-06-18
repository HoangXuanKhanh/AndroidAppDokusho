package haken.dokemi.andoku.service.models.dashboard

data class DashBoardResponse(
    val `data`: List<DashBoardResponseData>,
    val message: String,
    val status: Int
)