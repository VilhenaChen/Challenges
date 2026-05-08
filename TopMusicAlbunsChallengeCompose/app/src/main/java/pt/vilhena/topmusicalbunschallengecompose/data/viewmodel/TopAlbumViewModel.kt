package pt.vilhena.topmusicalbunschallengecompose.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import pt.vilhena.topmusicalbunschallengecompose.data.database.AlbumDao
import pt.vilhena.topmusicalbunschallengecompose.data.database.AlbumEntity
import pt.vilhena.topmusicalbunschallengecompose.data.dto.NetworkResult
import pt.vilhena.topmusicalbunschallengecompose.data.model.Album
import pt.vilhena.topmusicalbunschallengecompose.data.repo.TopAlbunsRepository
import javax.inject.Inject

@HiltViewModel
class TopAlbumViewModel @Inject constructor(
    private val albumRepository: TopAlbunsRepository,
    private val albumDao: AlbumDao
) : ViewModel() {

    companion object {
        private val TAG = TopAlbumViewModel::class.java.simpleName
    }

    private val _albumsState = MutableStateFlow<NetworkResult<List<Album>>>(NetworkResult.Loading)
    val albumsState = _albumsState.asStateFlow()

    init {
        getTopAlbumsList()
    }

    // Get Top Album list from API or DB if needed
    fun getTopAlbumsList() {
        Log.d(TAG, "Getting top albums")
        _albumsState.value = NetworkResult.Loading
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
                            _albumsState.value =  NetworkResult.Success(albumList)
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
            } catch (_: TimeoutCancellationException) {
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
        Log.d(TAG, "Getting from DB")
        val cachedAlbums = albumDao.getAllAlbums().map { it.toDomain() }
        if(cachedAlbums.isEmpty()) {
            Log.d(TAG, "DB is empty")
            _albumsState.value = NetworkResult.Error(message = "DB is empty")
        } else {
            Log.d(TAG, "DB is not empty")
            Log.d(TAG, "Albums: $cachedAlbums")
            _albumsState.value = NetworkResult.Success(cachedAlbums)
        }
    }

    // Add or Remove Album from favorite
    fun addOrRemoveFavorite(album: Album) {
        viewModelScope.launch {
            Log.d(TAG, "Changing favorite status for ${album.title} isFavorite: ${album.isFavorite}")
            val updatedAlbum = album.copy(isFavorite = !album.isFavorite)
            Log.d(TAG, "Updated album: $updatedAlbum")

            if(album.isFavorite) {
                albumDao.removeFavoriteAlbum(updatedAlbum.toEntity())
            } else {
                albumDao.addFavoriteAlbum(updatedAlbum.toEntity())
            }

            getAlbumListFromDB()
        }
    }

    // Get the album top list from DB
    fun orderListToTop() {
        viewModelScope.launch {
            getAlbumListFromDB()
        }
    }

    // Order the list Alphabetically
    fun orderListAlphabetically() {
        viewModelScope.launch {
            getAlbumListFromDB()
            val currentState = _albumsState.value
            if (currentState is NetworkResult.Success) {
                val sortedList = currentState.data.sortedBy { it.title }
                _albumsState.value = NetworkResult.Success(sortedList)
            }
        }
    }

    // Get only the favorites
    fun showOnlyFavorites() {
        viewModelScope.launch {
            val currentState = _albumsState.value
            if (currentState is NetworkResult.Success) {
                val sortedList = currentState.data.filter { it.isFavorite }
                _albumsState.value = NetworkResult.Success(sortedList)
            }
        }
    }
}
