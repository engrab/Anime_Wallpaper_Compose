package com.example.anime.data

import android.app.Application
import com.example.anime.BuildConfig
import com.example.anime.data.local.LocalDatabase
import com.example.anime.data.remote.unsplash.UnsplashApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/* This class can be moved to data, but will increase dependency in that module  */

@InstallIn(SingletonComponent::class)
@Module
object DataModule {
    @Provides
    @Singleton
    fun provideFavouritesDao(application: Application) =
        LocalDatabase.getNoteDatabase(application).getFavouriteDao()

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
