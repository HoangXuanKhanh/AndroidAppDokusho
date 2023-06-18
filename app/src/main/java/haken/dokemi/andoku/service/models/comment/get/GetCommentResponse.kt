package haken.dokemi.andoku.service.models.comment.get

data class GetCommentResponse(
    val `data`: List<GetCommentResponseData>,
    val message: String,
    val status: Int
)