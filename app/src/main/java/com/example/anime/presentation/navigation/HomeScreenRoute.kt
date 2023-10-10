package com.example.anime.presentation.navigation

import androidx.annotation.DrawableRes
import com.example.anime.R

sealed class HomeScreenRoute(val title: String, val route: String) {
    object Wallpapers : HomeScreenRoute("Wallpapers", "route_wallpapers")
    object Collections : HomeScreenRoute("Collections", "route_collections")
    object Favourites : HomeScreenRoute("Favourites", "route_favourites")
}
