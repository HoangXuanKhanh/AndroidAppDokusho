package haken.dokemi.andoku.service.models.story

data class StoryResponseData(
    val active: String,
    val category_id: String,
    val chapter_id: String,
    val content_story: String,
    val edit: String,
    val id: Int,
    val number_comment: String,
    val price: String,
    val slug_story: String,
    val source_story: String,
    val status: String,
    val story_free: String,
    val story_image: String,
    val story_name: String,
    val story_name_author: String,
    val story_new: String,
    val story_vote_star: String
)