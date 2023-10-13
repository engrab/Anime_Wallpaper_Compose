package com.example.anime.presentation.components

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import com.example.anime.data.model.Wallpaper
import com.example.anime.presentation.navigation.MainScreenRoute
import com.example.anime.presentation.screens.home.ErrorFooter
import com.example.anime.presentation.screens.home.ErrorHeader
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.gson.Gson

const val TAG = "WallpaperList"

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun WallpaperList(
    wallpapers: LazyPagingItems<Wallpaper>,
    favourites: List<Wallpaper>,
    addFavourite: (Wallpaper) -> Unit,
    removeFavourite: (String) -> Unit,
    state: LazyGridState = rememberLazyGridState(),
    navController: NavHostController
) {
    SwipeRefresh(
        modifier = Modifier
            .fillMaxSize(),
        state = rememberSwipeRefreshState(isRefreshing = (wallpapers.loadState.refresh is LoadState.Loading)),
        indicator = { swipeState, trigger ->
            SwipeRefreshIndicator(
                state = swipeState,
                refreshTriggerDistance = trigger,
                scale = true,
                backgroundColor = MaterialTheme.colors.primary,
                shape = MaterialTheme.shapes.small
            )
        },
        onRefresh = {
            wallpapers.refresh()
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxSize(),
            state = state
        ) {
            if (wallpapers.loadState.refresh is LoadState.Loading) {
                item {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
            if (wallpapers.loadState.refresh is LoadState.NotLoading) {
                items(
                    count = wallpapers.itemCount
                ) { index ->
                    wallpapers[index]?.let { wallpaper ->
                        val isFavourite = favourites.any { it.id == wallpaper.id }
                        UnsplashWallpaperCard(
                            modifier = Modifier
                                .padding(top = if (index == 0 || index == 1) 8.dp else 0.dp)
                                .padding(8.dp)
                                .fillMaxWidth()
                                .height(250.dp),
                            wallpaper = wallpaper,
                            onClick = {
                                val data = Uri.encode(Gson().toJson(wallpaper))
                                navController.navigate("${MainScreenRoute.SetWallpaper.route}/$data")
                            },
                            isFavourite = isFavourite,
                            onLikedClicked = {
                                if (isFavourite) {
                                    removeFavourite(wallpaper.id)
                                } else {
                                    addFavourite(
                                        wallpaper
                                    )
                                }
                            }
                        )
                    }
                }
            }
            if (wallpapers.loadState.refresh is LoadState.Error) {
                item {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(text = "Error Occurred", modifier = Modifier.clickable {
                            wallpapers.refresh()
                        })
                    }
                }
            }

            if (wallpapers.loadState.append is LoadState.Loading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
            if (wallpapers.loadState.append is LoadState.Error) {
                item {
                    ErrorFooter {
                        wallpapers.retry()
                    }
                }
            }

            if (wallpapers.loadState.prepend is LoadState.Loading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
            if (wallpapers.loadState.prepend is LoadState.Error) {
                item {
                    ErrorHeader {
                        wallpapers.retry()
                    }
                }
            }
//
//            items(
//                count = wallpapers.itemCount
//            ) { index ->
//                wallpapers[index]?.let { wallpaper ->
//                    val isFavourite = favourites.any { it.id == wallpaper.id }
//                    UnsplashWallpaperCard(
//                        modifier = Modifier
//                            .padding(top = if (index == 0 || index == 1) 8.dp else 0.dp)
//                            .padding(8.dp)
//                            .fillMaxWidth()
//                            .height(250.dp),
//                        wallpaper = wallpaper,
//                        onClick = {
//                            val data = Uri.encode(Gson().toJson(wallpaper))
//                            navController.navigate("${MainScreenRoute.SetWallpaper.route}/$data")
//                        },
//                        isFavourite = isFavourite,
//                        onLikedClicked = {
//                            if (isFavourite) {
//                                removeFavourite(wallpaper.id)
//                            } else {
//                                addFavourite(
//                                    wallpaper
//                                )
//                            }
//                        }
//                    )
//                }
//            }
//            wallpapers.apply {
//                val error: LoadState.Error? = when {
//                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
//                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
//                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
//                    else -> null
//                }
//                error?.let {
//                    item {
//                        Text(text = "No items found \n Error : " + it.error.message.toString())
//                    }
//                    item {
//                        Text(text = "Swipe down to refresh")
//                    }
//                }
//            }

        }
    }
}
