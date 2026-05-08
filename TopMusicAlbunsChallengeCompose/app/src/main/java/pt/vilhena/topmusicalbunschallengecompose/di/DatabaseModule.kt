package pt.vilhena.topmusicalbunschallengecompose.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pt.vilhena.topmusicalbunschallengecompose.data.database.AlbumDao
import pt.vilhena.topmusicalbunschallengecompose.data.database.AlbumDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AlbumDatabase =
        Room.databaseBuilder(
            context,
            AlbumDatabase::class.java,
            "albums_db"
        ).build()

    @Provides
    fun provideAlbumDao(
        database: AlbumDatabase
    ): AlbumDao = database.albumDao()
    
}