package com.example.anime.presentation.navigation

import androidx.annotation.DrawableRes
import com.example.anime.R

sealed class HomeScreen(val title: String, val route: String, @DrawableRes val resId: Int) {
    object Wallpapers : HomeScreen("Wallpapers", "route_wallpapers", R.drawable.ic_wallpapers)
    object Collections : HomeScreen("Collections", "route_collections", R.drawable.ic_collections)
    object Favourites : HomeScreen("Favourites", "route_favourites", R.drawable.ic_favourites)
}
