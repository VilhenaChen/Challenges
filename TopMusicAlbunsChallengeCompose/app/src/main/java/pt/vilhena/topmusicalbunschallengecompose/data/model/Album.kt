package pt.vilhena.topmusicalbunschallengecompose.data.model

import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.core.os.BundleCompat
import androidx.navigation.NavType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import pt.vilhena.topmusicalbunschallengecompose.data.database.AlbumEntity

@Parcelize
@Serializable
data class Album(
    val title: String,
    val imageUrl: String,
    val artistName: String,
    val releaseDate: String,
    val genre: String,
    val price: String,
    val isFavorite: Boolean = false
): Parcelable {

    fun toEntity() = AlbumEntity(
        title = title,
        artistName = artistName,
        imageUrl = imageUrl,
        releaseDate = releaseDate,
        genre = genre,
        price = price,
        isFavorite = isFavorite
    )

    companion object {
        val NavigationType = object : NavType<Album>(isNullableAllowed = false) {
            override fun get(bundle: Bundle, key: String): Album? {
                return BundleCompat.getParcelable(bundle, key, Album::class.java)
            }

            override fun parseValue(value: String): Album {
                return Json.decodeFromString(Uri.decode(value))
            }

            override fun put(bundle: Bundle, key: String, value: Album) {
                bundle.putParcelable(key, value)
            }

            override fun serializeAsValue(value: Album): String {
                return Uri.encode(Json.encodeToString(value))
            }
        }
    }
}

class SingleAlbumProvider : PreviewParameterProvider<Album> {
    override val values: Sequence<Album> = sequenceOf(
        Album(
            title = "Thriller",
            imageUrl = "https://is1-ssl.mzstatic.com/image/thumb/Music126/v4/91/3d/8d/913d8d3e-9f0e-5e3a-232a-d9f0e5e3a23a/886445077425.jpg/170x170bb.png",
            artistName = "Michael Jackson",
            releaseDate = "1982-11-30",
            genre = "Pop",
            price = "9.99",
            isFavorite = false
        )
    )
}
