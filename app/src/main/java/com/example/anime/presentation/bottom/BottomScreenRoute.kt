package com.example.anime.presentation.bottom

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomScreenRoute(val title: String, val route: String, val resId: ImageVector) {
    object Wallpapers : BottomScreenRoute("Wallpapers", "route_wallpapers", Icons.Default.Home)
    object Collections : BottomScreenRoute("Collections", "route_collections", Icons.Default.Collections)
    object Favourites : BottomScreenRoute("Favourites", "route_favourites", Icons.Default.Favorite)
    object Saved : BottomScreenRoute("Saved", "route_saved", Icons.Default.Home)
}
