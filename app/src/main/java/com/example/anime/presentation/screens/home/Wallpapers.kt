package com.example.anime.presentation.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.example.anime.data.model.Wallpaper
import com.example.anime.presentation.MainViewModel
import com.example.anime.presentation.components.WallpaperList

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun Wallpapers(
    viewModel: MainViewModel,
    wallpapers: LazyPagingItems<Wallpaper>,
    favourites: List<Wallpaper>,
    listState: LazyGridState,
    navController: NavHostController
) {
    Column(modifier = Modifier.fillMaxSize()) {
        WallpaperList(
            wallpapers = wallpapers,
            favourites = favourites,
            addFavourite = { viewModel.addFavourite(it) },
            removeFavourite = { viewModel.removeFavourite(it) },
            state = listState,
            navController = navController
        )
    }
}
