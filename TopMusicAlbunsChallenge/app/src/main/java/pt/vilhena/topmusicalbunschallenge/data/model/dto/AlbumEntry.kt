package pt.vilhena.topmusicalbunschallenge.data.model.dto

import com.google.gson.annotations.SerializedName
import pt.vilhena.topmusicalbunschallenge.data.model.Album

data class AlbumEntry(
    @SerializedName("im:name")
    val name: Label,

    @SerializedName("im:image")
    val images: List<Image>,

    val category: Category,

    @SerializedName("im:artist")
    val artistName: Label,

    @SerializedName( "im:releaseDate")
    val releaseDate: Label,

    @SerializedName( "im:price")
    val price: Label,

) {
    fun toAlbum(): Album {
        return Album(
            title = name.label,
            imageUrl = images.maxByOrNull { it.attributes.height }?.label.orEmpty(),
            genre = category.attributes.term,
            artistName = artistName.label,
            releaseDate = releaseDate.label,
            price = price.label
        )
    }
}


data class Label(
    val label: String
)

data class Image(
    val label: String,
    val attributes: ImageAttributes
)

data class ImageAttributes(
    val height: Int
)

data class Category(
    val attributes: CategoryAttributes
)

data class CategoryAttributes(
    val term: String
)