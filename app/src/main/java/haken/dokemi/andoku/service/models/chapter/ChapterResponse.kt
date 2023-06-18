package haken.dokemi.andoku.service.models.chapter

data class ChapterResponse(
    val code_status: Int,
    val `data`: List<ChapterResponseData>,
    val message: String
)