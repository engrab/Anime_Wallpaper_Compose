package com.example.anime.data.remote.unsplash

import com.example.anime.data.remote.unsplash.models.category.CategoryDto
import com.example.anime.data.remote.unsplash.models.wallpaper.WallpaperDto
import com.example.anime.data.remote.unsplash.models.wallpaper.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {

    @GET("/search/photos/")
    suspend fun getImages(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("orientation") orientation: String = "portrait",
        @Query("per_page") perPage: Int = 20
    ): Response

    @GET("/collections")
    suspend fun getCollection(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20
    ): List<CategoryDto>

    @GET("/collections/{id}/photos")
    suspend fun getPhotosByCollection(
        @Path("id") collectionId: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20
    ): List<WallpaperDto>

    @GET("/photos/{id}")
    suspend fun getImage(): WallpaperDto
}
