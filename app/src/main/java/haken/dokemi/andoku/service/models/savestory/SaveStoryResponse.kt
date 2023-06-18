package haken.dokemi.andoku.service.models.savestory

data class SaveStoryResponse(
    val `data`: List<SaveStoryResponseData>,
    val message: String,
    val status: Int
)