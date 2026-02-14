package pt.vilhena.topmusicalbunschallenge

import android.app.Application
import pt.vilhena.topmusicalbunschallenge.di.AppComponent
import pt.vilhena.topmusicalbunschallenge.di.DaggerAppComponent

class TopAlbumApplication: Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}