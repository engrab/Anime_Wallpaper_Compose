package com.example.anime.data.remote.unsplash

import com.example.anime.data.model.Collection
import com.example.anime.data.model.Wallpaper
import com.example.anime.data.remote.RemoteRepository
import com.example.anime.data.remote.unsplash.models.collection.CollectionDto
import com.example.anime.data.remote.unsplash.models.image.ImageDto

class UnsplashRepository(private val unsplashApi: UnsplashApi) : RemoteRepository {

    override suspend fun getWallpapers(page: Int, query: String): List<Wallpaper> {
        return unsplashApi.getImages(
            query = query,
            page = page
        ).results.map { imageDtoToWallpaper(it) }
    }

    override suspend fun getWallpaper(id: String): Wallpaper {
        return imageDtoToWallpaper(unsplashApi.getImage())
    }

    override suspend fun getCollections(page: Int): List<Collection> =
        unsplashApi.getCollection(page)
            .map { collectionDtoToCollection(it) }

    override suspend fun getWallpapersByCollection(
        collectionId: String,
        page: Int
    ): List<Wallpaper> =
        unsplashApi.getPhotosByCollection(collectionId, page)
            .map { imageDtoToWallpaper(it) }
}

fun imageDtoToWallpaper(imageDto: ImageDto): Wallpaper {
    return Wallpaper(
        id = imageDto.id,
        previewUrl = imageDto.urls.thumb,
        smallUrl = imageDto.urls.small,
        wallpaperUrl = imageDto.urls.full,
        user = imageDto.user.name,
        userImageURL = imageDto.user.profile_image.small
    )
}

fun collectionDtoToCollection(collectionDto: CollectionDto): Collection {
    return Collection(
        id = collectionDto.id,
        title = collectionDto.title,
        totalPhotos = collectionDto.total_photos,
        coverPhoto = collectionDto.cover_photo.urls.regular,
        tags = collectionDto.tags.map { it.title }
    )
}
