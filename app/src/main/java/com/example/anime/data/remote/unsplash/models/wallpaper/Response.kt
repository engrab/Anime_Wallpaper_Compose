package com.example.anime.data.remote.unsplash.models.wallpaper

import com.google.gson.annotations.SerializedName

data class Response(
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    val results: List<WallpaperDto>
)
