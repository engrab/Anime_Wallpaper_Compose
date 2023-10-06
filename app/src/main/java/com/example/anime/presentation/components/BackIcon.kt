package com.example.anime.presentation.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun BackIcon(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    IconButton(
        modifier = modifier,
        onClick = {
            navController.popBackStack()
        }
    ) {
        Icon(
            imageVector = Icons.Rounded.ArrowBack,
            contentDescription = "back icon",
            tint = MaterialTheme.colors.onPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BackButtonPreview() {
    BackIcon(
        navController = rememberNavController()
    )
}



