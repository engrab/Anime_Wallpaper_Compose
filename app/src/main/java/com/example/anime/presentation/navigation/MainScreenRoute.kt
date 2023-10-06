package com.example.anime.presentation.navigation

sealed class MainScreenRoute(val title: String, val route: String) {
    object Splash : MainScreenRoute(title = "Splash", route = "splash_route")
    object Home : MainScreenRoute(title = "Home", route = "home_route")
    object Search : MainScreenRoute(title = "Search", route = "search_route")
    object Gallery : MainScreenRoute(title = "Gallery", route = "gallery_route")
    object CategoryWallpaper : MainScreenRoute(title = "Search", route = "collection_wallpaper_route")
    object SetWallpaper : MainScreenRoute(title = "SetWallpaper", route = "set_wallpaper_route")
}
