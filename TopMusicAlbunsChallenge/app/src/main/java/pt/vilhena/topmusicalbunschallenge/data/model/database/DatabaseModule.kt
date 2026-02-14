package pt.vilhena.topmusicalbunschallenge.data.model.database

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ): AlbumDatabase =
        Room.databaseBuilder(
            application,
            AlbumDatabase::class.java,
            "albums_db"
        ).build()

    @Provides
    fun provideAlbumDao(
        database: AlbumDatabase
    ): AlbumDao = database.albumDao()
}