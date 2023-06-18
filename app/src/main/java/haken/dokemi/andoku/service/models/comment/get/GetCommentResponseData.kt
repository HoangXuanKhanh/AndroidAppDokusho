package haken.dokemi.andoku.service.models.comment.get

data class GetCommentResponseData(
    val comment: String,
    val created_at: String,
    val id: Int,
    val story_id: String,
    val updated_at: String,
    val user_id: String,
    val user_name: String
)