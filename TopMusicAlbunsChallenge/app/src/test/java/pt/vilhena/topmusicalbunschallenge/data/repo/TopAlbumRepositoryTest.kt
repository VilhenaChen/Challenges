package pt.vilhena.topmusicalbunschallenge.data.repo

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import pt.vilhena.topmusicalbunschallenge.data.model.dto.NetworkResult
import pt.vilhena.topmusicalbunschallenge.data.model.dto.TopAlbumResponse
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
class TopAlbumRepositoryTest {
    private lateinit var repository: TopAlbumRepository
    private val itunesService: ItunesService = mock()

    @Before
    fun setup() {
        repository = TopAlbumRepository(itunesService)
    }

    @Test
    fun `getAlbums returns Success when response is successful`() = runTest {
        // GIVEN
        val responseBody = mock<TopAlbumResponse>()

        whenever(itunesService.getTopAlbums()).thenReturn(
            Response.success(responseBody)
        )

        // WHEN
        val result = repository.getAlbums()

        // THEN
        assertTrue(result is NetworkResult.Success)
        assertEquals(responseBody, (result as NetworkResult.Success).data)
    }

    @Test
    fun `getAlbums returns Error when body is null`() = runTest {
        // GIVEN
        whenever(itunesService.getTopAlbums()).thenReturn(
            Response.success(null)
        )

        // WHEN
        val result = repository.getAlbums()

        // THEN
        assertTrue(result is NetworkResult.Error)
        assertEquals("Empty body response", (result as NetworkResult.Error).message)
    }

    @Test
    fun `getAlbums returns Error when response is not successful`() = runTest {
        // GIVEN
        val errorResponse = Response.error<TopAlbumResponse>(
            404,
            "Not Found".toResponseBody("text/plain".toMediaType())
        )

        whenever(itunesService.getTopAlbums()).thenReturn(errorResponse)

        // WHEN
        val result = repository.getAlbums()

        // THEN
        assertTrue(result is NetworkResult.Error)
        assertEquals(404, errorResponse.code())
    }

    @Test
    fun `getAlbums returns Error on IOException`() = runTest {
        doAnswer {
            throw IOException()
        }.whenever(itunesService).getTopAlbums()

        // WHEN
        val result = repository.getAlbums()

        // THEN
        assertTrue(result is NetworkResult.Error)
    }
}