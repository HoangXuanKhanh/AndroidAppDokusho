package haken.dokemi.andoku.service.models.inform

data class InformResponse(
    val code_status: Int,
    val `data`: List<InformResponseData>,
    val message: String
)