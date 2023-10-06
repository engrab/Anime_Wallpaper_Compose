package com.example.anime.di

import android.app.Application
import com.example.anime.BuildConfig
import com.example.anime.data.database.LocalDatabase
import com.example.anime.data.database.LocalRepository
import com.example.anime.data.database.favourites.FavouriteDao
import com.example.anime.data.remote.RemoteRepository
import com.example.anime.data.remote.unsplash.UnsplashApi
import com.example.anime.data.remote.unsplash.UnsplashRepositoryImpl
import com.example.anime.domain.repository.WallpaperRepository
import com.example.anime.domain.repository.WallpaperRepositoryImpl
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @Named("unsplash")
    fun provideUnsplashRepository(
        unsplashApi: UnsplashApi
    ): RemoteRepository =
        UnsplashRepositoryImpl(unsplashApi)

    @Provides
    @Singleton
    fun provideLocalRepository(
        context: Application,
        favouriteDao: FavouriteDao
    ): LocalRepository = LocalRepository(context, favouriteDao)

    @Provides
    @Singleton
    fun provideWallpaperRepository(
        @Named("unsplash") remoteRepository: RemoteRepository,
        localRepository: LocalRepository
    ): WallpaperRepository =
        WallpaperRepositoryImpl(remoteRepository, localRepository)


    @Provides
    @Singleton
    fun provideFavouritesDao(application: Application): FavouriteDao =
        LocalDatabase.getWallpaperDatabase(application).getFavouriteDao()

    @Provides
    @Singleton
    fun provideUnsplashApi(): UnsplashApi = Retrofit.Builder().baseUrl("https://api.unsplash.com/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .client(
            OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val req = chain.request()
                    val newUrl = req.url().newBuilder()
                        .addQueryParameter("client_id", BuildConfig.UNSPLASH_API_KEY)
                        .build()
                    val newReq = req.newBuilder().url(newUrl).build()
                    chain.proceed(newReq)
                }
                .build()
        )
        .build()
        .create(UnsplashApi::class.java)
}