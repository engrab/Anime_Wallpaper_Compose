package com.example.anime.domain.usecases

import com.example.anime.domain.usecases.collections.GetCollections
import com.example.anime.domain.usecases.downloads.DownloadWallpaper
import com.example.anime.domain.usecases.favourites.AddFavourite
import com.example.anime.domain.usecases.favourites.GetFavourites
import com.example.anime.domain.usecases.favourites.RemoveFavourite
import com.example.anime.domain.usecases.wallpapers.GetWallpaper
import com.example.anime.domain.usecases.wallpapers.GetWallpapers
import com.example.anime.domain.usecases.wallpapers.GetWallpapersByCollection
import com.example.anime.domain.usecases.wallpapers.SetWallpaperUseCases
import javax.inject.Inject

data class WallpaperUseCase
@Inject
constructor(
    val getWallpapers: GetWallpapers,
    val getWallpaper: GetWallpaper,
    val getCollections: GetCollections,
    val getWallpapersByCollection: GetWallpapersByCollection,

    val downloadWallpaper: DownloadWallpaper,
    val setWallpaperUseCases: SetWallpaperUseCases,

    val getFavourites: GetFavourites,
    val addFavourite: AddFavourite,
    val removeFavourite: RemoveFavourite
)
