package pt.vilhena.topmusicalbunschallenge.data.repo

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val ITUNESRSS_URL = "https:///itunes.apple.com/us/rss/"
    }

    @Provides
    @Singleton
    //  Build Client with Auth Interceptor
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().build()

    @Provides
    @Singleton
    // Create Retrofit instance
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
        .baseUrl(ITUNESRSS_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideItunesService(retrofit: Retrofit): ItunesService =
        retrofit.create(ItunesService::class.java)
}