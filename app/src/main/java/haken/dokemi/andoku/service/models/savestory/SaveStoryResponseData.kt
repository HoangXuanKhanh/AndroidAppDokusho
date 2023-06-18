package haken.dokemi.andoku.service.models.savestory

data class SaveStoryResponseData(
    val id: Int,
    val story_image: String,
    val story_name: String,
    val story_name_author: String,
    val story_vote_star: Int
)