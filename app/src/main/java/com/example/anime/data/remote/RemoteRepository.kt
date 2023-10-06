package com.example.anime.data.remote

import com.example.anime.data.model.Category
import com.example.anime.data.model.Wallpaper

interface RemoteRepository {
    suspend fun getWallpapers(page: Int, query: String): List<Wallpaper>
    suspend fun getWallpaper(id: String): Wallpaper
    suspend fun getCollections(page: Int): List<Category>
    suspend fun getWallpapersByCollection(collectionId: String, page: Int): List<Wallpaper>
}
