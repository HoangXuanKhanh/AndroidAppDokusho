package haken.dokemi.andoku.service.models.story

data class StoryResponse(
    val code_status: Int,
    val `data`: List<StoryResponseData>,
    val message: String
)