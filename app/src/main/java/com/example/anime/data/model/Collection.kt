package com.example.anime.data.model

data class Collection(
    val id: String,
    val title: String,
    val totalPhotos: Int,
    val coverPhoto: String,
    val tags: List<String>
)
