package com.example.anime.presentation.drawer

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.anime.presentation.navigation.MainScreenRoute

@Composable
fun AppBar(navController : NavHostController,
    onNavigationIconClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = "Wallpaper")
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Toggle drawer"
                )
            }
        },
        actions ={

            IconButton(onClick = {
                navController.navigate(MainScreenRoute.Search.route)
            }) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search"
                )
            }
//            IconButton(onClick = {
//
//            }) {
//                Icon(
//                    imageVector = Icons.Rounded.AccountBalanceWallet,
//                    contentDescription = "Remove Ads"
//                )
//            }

        }

    )
}