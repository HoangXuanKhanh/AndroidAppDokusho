package haken.dokemi.andoku.service.models.comment.post

data class PostCommentResponse(
    val code_status: Int,
    val `data`: PostCommentResponseData,
    val message: String
)