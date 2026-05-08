package pt.vilhena.topmusicalbunschallengecompose.ui.navigation

import kotlinx.serialization.Serializable
import pt.vilhena.topmusicalbunschallengecompose.data.model.Album

@Serializable
object AlbumTopListRoute

@Serializable
data class AlbumDetailsRoute(val album: Album)
