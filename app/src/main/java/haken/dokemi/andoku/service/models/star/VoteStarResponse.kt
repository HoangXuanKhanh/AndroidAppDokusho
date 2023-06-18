package haken.dokemi.andoku.service.models.star

data class VoteStarResponse(
    val `data`: VoteStarResponseData,
    val message: String,
    val status: Int
)