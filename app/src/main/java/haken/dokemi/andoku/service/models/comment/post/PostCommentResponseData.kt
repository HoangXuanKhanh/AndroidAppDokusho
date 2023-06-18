package haken.dokemi.andoku.service.models.comment.post

data class PostCommentResponseData(
    val comment: String,
    val created_at: String,
    val id: Int,
    val story_id: String,
    val updated_at: String,
    val user_id: Int,
    val user_name: String
)