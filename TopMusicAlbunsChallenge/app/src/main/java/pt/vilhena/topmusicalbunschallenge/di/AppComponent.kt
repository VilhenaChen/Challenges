package pt.vilhena.topmusicalbunschallenge.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import pt.vilhena.topmusicalbunschallenge.MainActivity
import pt.vilhena.topmusicalbunschallenge.data.model.database.DatabaseModule
import pt.vilhena.topmusicalbunschallenge.data.repo.NetworkModule
import pt.vilhena.topmusicalbunschallenge.ui.fragment.AlbumDetailedInformationFragment
import pt.vilhena.topmusicalbunschallenge.ui.fragment.AlbunsListFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: AlbunsListFragment)
    fun inject(fragment: AlbumDetailedInformationFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}