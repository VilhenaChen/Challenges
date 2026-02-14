package pt.vilhena.topmusicalbunschallenge.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import pt.vilhena.topmusicalbunschallenge.data.model.Album
import pt.vilhena.topmusicalbunschallenge.data.model.database.AlbumDao
import pt.vilhena.topmusicalbunschallenge.data.model.database.AlbumEntity
import pt.vilhena.topmusicalbunschallenge.data.model.dto.NetworkResult
import pt.vilhena.topmusicalbunschallenge.data.repo.TopAlbumRepository
import javax.inject.Inject

class TopAlbumViewModel @Inject constructor(
    private val albumRepository: TopAlbumRepository,
    private val albumDao: AlbumDao
) : ViewModel() {

    private val _albumsLiveData =  MutableLiveData<NetworkResult<List<Album>>>()
    val albumsLiveData: LiveData<NetworkResult<List<Album>>> = _albumsLiveData

    companion object {
        const val TAG = "TopAlbumViewModel"
    }

    // Get Top Album list from API or DB if needed
    fun getTopAlbumsList() {
        Log.d(TAG, "Getting top albums")
        _albumsLiveData.value = NetworkResult.Loading
        viewModelScope.launch {
            try {
                withTimeout(5000) {
                    when(val response = albumRepository.getAlbums()) {
                        is NetworkResult.Success -> {
                            Log.d(TAG, "Success  response: $response")
                            val albumList = response.data.feed.entry.map {
                                val album = it.toAlbum()
                                Log.d(TAG, "Got album = $album")
                                album
                            }
                            insertIntoDB(albumList.map { it.toEntity() })
                            _albumsLiveData.postValue(
                                NetworkResult.Success(albumList)
                            )

                        }
                        is NetworkResult.Error -> {
                            Log.d(TAG, "Error: ${response.message}")
                            getAlbumListFromDB()
                        }
                        else -> {
                            Log.d(TAG, "Loading")
                        }
                    }
                }
            } catch (e: TimeoutCancellationException) {
                Log.d(TAG, "Network Timeout Occurred, trying to get from DB")
                getAlbumListFromDB()
            } catch (e: Exception) {
                Log.d(TAG, e.printStackTrace().toString())
                getAlbumListFromDB()

            }
        }

    }

    // Insert new list into DB
    private fun insertIntoDB(albums: List<AlbumEntity>) {
        viewModelScope.launch {
            Log.d(TAG, "Inserting: ${albums.size}")
            albumDao.clearAlbums()
            albumDao.insertAlbums(albums)
        }
    }

    // Getting Albums from DB when offline/network problem
    private suspend fun getAlbumListFromDB() {
        val cachedAlbums = albumDao.getAlbums().map { it.toDomain() }
        if(cachedAlbums.isEmpty()) {
            Log.d(TAG, "DB is empty")
            _albumsLiveData.postValue(
                NetworkResult.Error(message = "DB is empty"))
        } else {
            _albumsLiveData.postValue(
                NetworkResult.Success(cachedAlbums)
            )
        }
    }
}