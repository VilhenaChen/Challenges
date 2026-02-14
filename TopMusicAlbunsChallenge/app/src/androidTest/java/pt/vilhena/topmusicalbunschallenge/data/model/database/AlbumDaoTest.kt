package pt.vilhena.topmusicalbunschallenge.data.model.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue


@ExperimentalCoroutinesApi
class AlbumDaoTest {

    private lateinit var database: AlbumDatabase
    private lateinit var albumDao: AlbumDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AlbumDatabase::class.java
        )
            .allowMainThreadQueries() // só em testes!
            .build()

        albumDao = database.albumDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndReadAlbums() = runTest {
        // GIVEN
        val albums = listOf(
            AlbumEntity(1, "Album 1", "Artist 1", "url1", "2022-01-01", "genre", "30"),
            AlbumEntity(2, "Album 2", "Artist 2", "url2","2022-01-01", "genre", "30")
        )

        // WHEN
        albumDao.insertAlbums(albums)
        val result = albumDao.getAlbums()

        // THEN
        assertEquals(2, result.size)
        assertEquals("Album 1", result[0].title)
    }

    @Test
    fun clearAlbumsDeletesAll() = runTest {
        // GIVEN
        albumDao.insertAlbums(
            listOf(AlbumEntity(1, "Album 1", "Artist 1", "url1", "2022-01-01", "genre", "30"))
        )

        // WHEN
        albumDao.clearAlbums()

        // THEN
        val result = albumDao.getAlbums()
        assertTrue(result.isEmpty())
    }

    @Test
    fun insertReplacesOnConflict() = runTest {
        // GIVEN
        val album1 = AlbumEntity(1, "Album 1", "Artist 1", "url1", "2022-01-01", "genre", "30")
        val album2 = AlbumEntity(1, "New name", "Artist 1", "url1", "2022-01-01", "genre", "30")

        // WHEN
        albumDao.insertAlbums(listOf(album1))
        albumDao.insertAlbums(listOf(album2))

        // THEN
        val result = albumDao.getAlbums()
        assertEquals(1, result.size)
        assertEquals("New name", result.first().title)
    }

}