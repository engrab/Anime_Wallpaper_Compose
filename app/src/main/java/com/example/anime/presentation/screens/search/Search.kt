package com.example.anime.presentation.screens.search

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.example.anime.data.model.Wallpaper
import com.example.anime.presentation.components.BackButton
import com.example.anime.presentation.components.SearchBar
import com.example.anime.presentation.components.WallpaperList

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun Search(
    navController: NavHostController,
    favourites: List<Wallpaper>,
    addFavourite: (Wallpaper) -> Unit,
    removeFavourite: (String) -> Unit
) {
    val viewModel: SearchViewModel = hiltViewModel()
    val dataFlow by remember { viewModel.wallpapers }
    val wallpapers = dataFlow.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                elevation = 0.dp,
                color = MaterialTheme.colors.primary
            ) {
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    BackButton(navController = navController)
                    SearchBar(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(0.8f),
                        onSearch = { viewModel.search(it) }
                    )
                }
            }
        }
    ) {
        WallpaperList(
            wallpapers = wallpapers,
            favourites = favourites,
            addFavourite = { wallpaper ->
                addFavourite(wallpaper)
            },
            removeFavourite = { id ->
                removeFavourite(id)
            },
            navController = navController
        )
    }
}
