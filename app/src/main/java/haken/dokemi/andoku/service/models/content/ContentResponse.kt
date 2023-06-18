package haken.dokemi.andoku.service.models.content

data class ContentResponse(
    val code_status: Int,
    val `data`: List<ContentResponseData>,
    val message: String
)