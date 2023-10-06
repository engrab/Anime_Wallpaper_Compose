package com.example.anime.data.remote.unsplash.models.category

data class CategoryDto(
    val id: String,
    val title: String,
    val total_photos: Int,
    val cover_photo: CoverPhoto,
    val tags: List<Tag>,

)
