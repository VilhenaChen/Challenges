package pt.vilhena.topmusicalbunschallengecompose.data

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import pt.vilhena.topmusicalbunschallengecompose.R
import pt.vilhena.topmusicalbunschallengecompose.data.model.Album


// UTILS FOR PREVIEWS

// Provide only one album
class SingleAlbumProvider : PreviewParameterProvider<Album> {
    override val values: Sequence<Album> = sequenceOf(
        Album(
            title = "Thriller",
            imageUrl = "android.resource://pt.vilhena.topmusicalbunschallengecompose/${R.drawable.album_art_placeholder}",
            artistName = "Michael Jackson",
            releaseDate = "1982",
            genre = "Pop",
            price = "19.99",
            isFavorite = false
        )
    )
}

// Provide a list of albums
class MultipleAlbumProvider : PreviewParameterProvider<List<Album>> {
    override val values: Sequence<List<Album>> = sequenceOf(
        listOf(
            Album(
                title = "Thriller",
                imageUrl = "android.resource://pt.vilhena.topmusicalbunschallengecompose/${R.drawable.album_art_placeholder}",
                artistName = "Michael Jackson",
                releaseDate = "1982",
                genre = "Pop",
                price = "19.99",
                isFavorite = false
            ),
            Album(
                title = "Bad",
                imageUrl = "android.resource://pt.vilhena.topmusicalbunschallengecompose/${R.drawable.album_art_placeholder}",
                artistName = "Michael Jackson",
                releaseDate = "1987",
                genre = "Pop",
                price = "19.99",
                isFavorite = true
            )
        )
    )
}