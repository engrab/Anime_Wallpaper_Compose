package com.example.anime.presentation.navigation

import androidx.annotation.DrawableRes
import com.example.anime.R

sealed class HomeScreenRoute(val title: String, val route: String, @DrawableRes val resId: Int) {
    object Wallpapers : HomeScreenRoute("Wallpapers", "route_wallpapers", R.drawable.ic_wallpapers)
    object Collections : HomeScreenRoute("Collections", "route_collections", R.drawable.ic_collections)
    object Favourites : HomeScreenRoute("Favourites", "route_favourites", R.drawable.ic_favourites)
    object Saved : HomeScreenRoute("Saved", "route_saved", R.drawable.ic_favourites)
}
