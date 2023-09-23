package com.example.anime.presentation.anim

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.ui.Alignment

object SetWallpaperAnimations {
    @ExperimentalAnimationApi
    fun enterTransition(): EnterTransition {
        return expandIn(expandFrom = Alignment.Center, animationSpec = tween(durationMillis = 1000))
    }

    @ExperimentalAnimationApi
    fun exitTransition(): ExitTransition {
        return shrinkOut(shrinkTowards = Alignment.Center, animationSpec = tween(durationMillis = 1000))
    }
}
