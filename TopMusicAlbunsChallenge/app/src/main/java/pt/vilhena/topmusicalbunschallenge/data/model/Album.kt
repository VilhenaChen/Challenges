package pt.vilhena.topmusicalbunschallenge.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import pt.vilhena.topmusicalbunschallenge.data.model.database.AlbumEntity

@Parcelize
data class Album(
    val title: String,
    val imageUrl: String,
    val artistName: String,
    val releaseDate: String,
    val genre: String,
    val price: String
): Parcelable {

    //  Parse for DB Item
    fun toEntity() = AlbumEntity(
        title = title,
        artistName = artistName,
        imageUrl = imageUrl,
        releaseDate = releaseDate,
        genre = genre,
        price = price
    )
}