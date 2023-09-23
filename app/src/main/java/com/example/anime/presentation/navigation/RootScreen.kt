package com.example.anime.presentation.navigation

sealed class RootScreen(val title: String, val route: String) {
    object Splash : RootScreen(title = "Splash", route = "splash_route")
    object Home : RootScreen(title = "Home", route = "home_route")
    object Search : RootScreen(title = "Search", route = "search_route")
    object Gallery : RootScreen(title = "Gallery", route = "gallery_route")
    object CollectionWallpaper : RootScreen(title = "Search", route = "collection_wallpaper_route")
    object SetWallpaper : RootScreen(title = "SetWallpaper", route = "set_wallpaper_route")
}
