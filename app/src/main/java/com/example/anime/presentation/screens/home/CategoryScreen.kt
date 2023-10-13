package com.example.anime.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.example.anime.data.model.Category
import com.example.anime.presentation.components.CategoryScreen
import com.example.anime.presentation.navigation.MainScreenRoute
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun CategoryScreen(
    collections: LazyPagingItems<Category>,
    listState: LazyListState,
    navController: NavHostController
) {
    SwipeRefresh(
        modifier = Modifier
            .fillMaxSize(),
        state = rememberSwipeRefreshState(isRefreshing = (collections.loadState.refresh is LoadState.Loading)),
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
            collections.refresh()
        }
    ) {
//        LazyColumn(modifier = Modifier.fillMaxSize(), state = listState) {
//
//            items(collections) { collection ->
//                collection?.let {
//                    CategoryScreen(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(180.dp)
//                            .padding(horizontal = 16.dp, vertical = 8.dp),
//                        title = collection.title,
//                        backgroundImage = collection.coverPhoto,
//                        totalPhoto = collection.totalPhotos
//                    ) {
//                        navController.navigate(MainScreenRoute.CategoryWallpaper.route.plus("/${it.id}/${it.title}"))
//                    }
//                }
//            }
//            collections.apply {
//                val error = when {
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
//        }

        LazyColumn(modifier = Modifier.fillMaxSize(), state = listState) {


            if (collections.loadState.refresh is LoadState.Loading) {
                item {
                    Box(modifier = Modifier.fillParentMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
            if (collections.loadState.refresh is LoadState.NotLoading) {
                items(collections) { collection ->
                    collection?.let {
                        CategoryScreen(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            title = collection.title,
                            backgroundImage = collection.coverPhoto,
                            totalPhoto = collection.totalPhotos
                        ) {
                            navController.navigate(MainScreenRoute.CategoryWallpaper.route.plus("/${it.id}/${it.title}"))
                        }
                    }
                }
            }
            if (collections.loadState.refresh is LoadState.Error) {
                item {
                    Box(modifier = Modifier.fillParentMaxSize()) {
                        Text(text = "Error Occurred", modifier = Modifier.clickable {
                            collections.refresh()
                        })
                    }
                }
            }

            if (collections.loadState.append is LoadState.Loading) {
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
            if (collections.loadState.append is LoadState.Error) {
                item {
                    ErrorFooter {
                        collections.retry()
                    }
                }
            }

            if (collections.loadState.prepend is LoadState.Loading) {
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
            if (collections.loadState.prepend is LoadState.Error) {
                item {
                    ErrorHeader {
                        collections.retry()
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorHeader(retry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(
            text = "Tap to Retry",
            modifier = Modifier
                .clickable { retry.invoke() }
                .align(Alignment.Center),
            style = MaterialTheme.typography.caption
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ErrorFooter(retry: () -> Unit = {}) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(imageVector = Icons.Default.Warning, contentDescription = null)
            Text(
                text = "Error Occurred",
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Retry",
                    modifier = Modifier
                        .clickable { retry.invoke() }
                        .align(Alignment.CenterEnd),
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }

}

