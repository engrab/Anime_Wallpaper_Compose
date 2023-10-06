package com.example.anime.presentation.anim

import androidx.compose.animation.*
import androidx.navigation.NavBackStackEntry
import com.example.anime.presentation.navigation.HomeScreenRoute

object FavouritesAnimations {
    @ExperimentalAnimationApi
    fun enterAnimation(initial: NavBackStackEntry): EnterTransition {
        return when (initial.destination.route) {
            HomeScreenRoute.Wallpapers.route, HomeScreenRoute.Collections.route -> {
                slideInHorizontally(initialOffsetX = { 1000 })
            }
            else -> {
                slideInHorizontally()
            }
        }
    }

    @ExperimentalAnimationApi
    fun exitAnimation(target: NavBackStackEntry): ExitTransition {
        return when (target.destination.route) {
            HomeScreenRoute.Wallpapers.route, HomeScreenRoute.Collections.route -> {
                slideOutHorizontally(targetOffsetX = { 1000 })
            }
            else -> {
                slideOutHorizontally(targetOffsetX = { -1000 })
            }
        }
    }
}
