package com.example.anime.domain

import android.app.Application
import com.example.anime.data.local.LocalRepository
import com.example.anime.data.local.favourites.FavouriteDao
import com.example.anime.data.remote.RemoteRepository
import com.example.anime.data.remote.unsplash.UnsplashApi
import com.example.anime.data.remote.unsplash.UnsplashRepository
import com.example.anime.domain.repository.WallpaperRepository
import com.example.anime.domain.repository.WallpaperRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WallpaperModule {

    @Provides
    @Singleton
    @Named("unsplash")
    fun provideUnsplashRepository(
        unsplashApi: UnsplashApi
    ): RemoteRepository =
        UnsplashRepository(unsplashApi)

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
}
