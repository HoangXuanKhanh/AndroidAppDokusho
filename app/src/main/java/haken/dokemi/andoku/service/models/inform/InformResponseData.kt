package haken.dokemi.andoku.service.models.inform

data class InformResponseData(
    val category: List<InformResponseCategory>,
    val content_story: String,
    val edit: String,
    val id: Int,
    val number_comment: String,
    val source_story: String,
    val status: String,
    val story_image: String,
    val story_name: String,
    val story_name_author: String,
    val story_vote_star: String
)