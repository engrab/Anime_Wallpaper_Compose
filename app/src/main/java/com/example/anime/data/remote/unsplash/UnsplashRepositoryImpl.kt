package com.example.anime.data.remote.unsplash

import com.example.anime.data.model.Category
import com.example.anime.data.model.Wallpaper
import com.example.anime.data.remote.RemoteRepository
import com.example.anime.data.remote.unsplash.models.category.CategoryDto
import com.example.anime.data.remote.unsplash.models.wallpaper.WallpaperDto

class UnsplashRepositoryImpl(private val unsplashApi: UnsplashApi) : RemoteRepository {

    override suspend fun getWallpapers(page: Int, query: String): List<Wallpaper> {
        return unsplashApi.getImages(
            query = query,
            page = page
        ).results.map { imageDtoToWallpaper(it) }
    }

    override suspend fun getWallpaper(id: String): Wallpaper {
        return imageDtoToWallpaper(unsplashApi.getImage())
    }

    override suspend fun getCollections(page: Int): List<Category> =
        unsplashApi.getCollection(page)
            .map { collectionDtoToCollection(it) }

    override suspend fun getWallpapersByCollection(
        collectionId: String,
        page: Int
    ): List<Wallpaper> =
        unsplashApi.getPhotosByCollection(collectionId, page)
            .map { imageDtoToWallpaper(it) }
}

fun imageDtoToWallpaper(wallpaperDto: WallpaperDto): Wallpaper {
    return Wallpaper(
        id = wallpaperDto.id,
        previewUrl = wallpaperDto.urls.thumb,
        smallUrl = wallpaperDto.urls.small,
        wallpaperUrl = wallpaperDto.urls.full,
        user = wallpaperDto.user.name,
        userImageURL = wallpaperDto.user.profile_image.small
    )
}

fun collectionDtoToCollection(categoryDto: CategoryDto): Category {
    return Category(
        id = categoryDto.id,
        title = categoryDto.title,
        totalPhotos = categoryDto.total_photos,
        coverPhoto = categoryDto.cover_photo.urls.regular,
        tags = categoryDto.tags.map { it.title }
    )
}
