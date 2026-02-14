package pt.vilhena.topmusicalbunschallenge.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.vilhena.topmusicalbunschallenge.data.model.database.AlbumDao
import pt.vilhena.topmusicalbunschallenge.data.repo.TopAlbumRepository
import pt.vilhena.topmusicalbunschallenge.data.viewmodel.TopAlbumViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val topAlbumRepository: TopAlbumRepository,
    private val albumDao: AlbumDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopAlbumViewModel::class.java)) {
            return TopAlbumViewModel(topAlbumRepository, albumDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}