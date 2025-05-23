package com.example.anime.domain.usecases.favourites

import com.example.anime.data.model.Wallpaper
import com.example.anime.domain.repository.WallpaperRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavourites
@Inject
constructor(
    private val repository: WallpaperRepository
) {
    operator fun invoke(): Flow<List<Wallpaper>> = repository.getFavourites()
}

class AddFavourite
@Inject
constructor(
    private val repository: WallpaperRepository
) {
    suspend operator fun invoke(wallpaper: Wallpaper) = repository.addFavourite(wallpaper)
}

class RemoveFavourite
@Inject
constructor(
    private val repository: WallpaperRepository
) {
    suspend operator fun invoke(id: String) = repository.removeFavourite(id)
}
