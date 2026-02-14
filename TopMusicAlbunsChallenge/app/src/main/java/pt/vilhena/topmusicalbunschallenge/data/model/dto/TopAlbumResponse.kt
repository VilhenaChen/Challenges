package pt.vilhena.topmusicalbunschallenge.data.model.dto

data class TopAlbumResponse(
    val feed: Feed
)

data class Feed(
    val entry: List<AlbumEntry>
)
