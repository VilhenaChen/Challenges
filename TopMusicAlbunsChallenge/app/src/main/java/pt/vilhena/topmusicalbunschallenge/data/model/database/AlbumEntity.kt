package pt.vilhena.topmusicalbunschallenge.data.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import pt.vilhena.topmusicalbunschallenge.data.model.Album

@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val title: String,
    val artistName: String,
    val imageUrl: String,
    val releaseDate: String,
    val genre: String,
    val price: String
) {

    //  Parse for Album Item
    fun toDomain() = Album(
        title = title,
        artistName = artistName,
        imageUrl = imageUrl,
        releaseDate = releaseDate,
        genre = genre,
        price = price
    )
}
