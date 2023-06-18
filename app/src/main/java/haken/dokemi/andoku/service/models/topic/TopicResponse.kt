package haken.dokemi.andoku.service.models.topic

data class TopicResponse(
    val code_status: Int,
    val `data`: List<TopicResponseData>,
    val message: String
)