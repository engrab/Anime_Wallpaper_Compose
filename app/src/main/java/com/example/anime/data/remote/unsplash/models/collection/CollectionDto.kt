package com.example.anime.data.remote.unsplash.models.collection

data class CollectionDto(
    val id: String,
    val title: String,
    val total_photos: Int,
    val cover_photo: CoverPhoto,
    val tags: List<Tag>,

)
