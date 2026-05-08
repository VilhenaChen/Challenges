package pt.vilhena.topmusicalbunschallengecompose.data.dto

data class TopAlbumResponse(
    val feed: Feed
)

data class Feed(
    val entry: List<AlbumEntry>
)
