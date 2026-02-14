package pt.vilhena.topmusicalbunschallenge.data.repo

import pt.vilhena.topmusicalbunschallenge.data.model.dto.TopAlbumResponse
import retrofit2.Response
import retrofit2.http.GET

interface ItunesService {

    @GET("topalbums/limit=100/json")
    suspend fun getTopAlbums(): Response<TopAlbumResponse>

}