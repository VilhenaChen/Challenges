package pt.vilhena.topmusicalbunschallengecompose.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import pt.vilhena.topmusicalbunschallengecompose.data.model.Album

@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey
    val title: String,
    val artistName: String,
    val imageUrl: String,
    val releaseDate: String,
    val genre: String,
    val price: String,
    val isFavorite: Boolean = false
) {
    //  Parse for Album Item
    fun toDomain() = Album(
        title = title,
        artistName = artistName,
        imageUrl = imageUrl,
        releaseDate = releaseDate,
        genre = genre,
        price = price,
        isFavorite = isFavorite
    )
}
