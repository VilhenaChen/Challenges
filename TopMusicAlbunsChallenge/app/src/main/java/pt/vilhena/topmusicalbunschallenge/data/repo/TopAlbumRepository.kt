package pt.vilhena.topmusicalbunschallenge.data.repo

import pt.vilhena.topmusicalbunschallenge.data.model.dto.NetworkResult
import pt.vilhena.topmusicalbunschallenge.data.model.dto.TopAlbumResponse
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TopAlbumRepository @Inject constructor(private val itunesService: ItunesService) {

    suspend fun getAlbums(): NetworkResult<TopAlbumResponse> {

        return try {
            val response = itunesService.getTopAlbums()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    NetworkResult.Success(body)
                } else {
                    NetworkResult.Error("Empty body response")
                }
            } else {
                NetworkResult.Error(
                    message = response.errorBody()?.string() ?: "Unknown Error",
                    code = response.code()
                )
            }
        }catch (e: IOException) {
            NetworkResult.Error("IOError Check Internet Connection")
        } catch (e: HttpException) {
            NetworkResult.Error("HTTP Error: ${e.code()}", e.code())
        } catch (e: Exception) {
            NetworkResult.Error("Unknown Error: ${e.localizedMessage}")
        }
    }
}