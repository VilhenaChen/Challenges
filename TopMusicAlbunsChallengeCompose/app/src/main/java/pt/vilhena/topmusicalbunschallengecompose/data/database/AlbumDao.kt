package pt.vilhena.topmusicalbunschallengecompose.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface AlbumDao {

    @Query("SELECT * FROM albums")
    suspend fun getAllAlbums(): List<AlbumEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<AlbumEntity>)

    @Query("DELETE FROM albums")
    suspend fun clearAlbums()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteAlbum(album: AlbumEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun removeFavoriteAlbum(album: AlbumEntity)

}