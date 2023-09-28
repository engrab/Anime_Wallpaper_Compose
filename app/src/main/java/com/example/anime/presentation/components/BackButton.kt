package com.example.anime.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun BackButton(
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
            contentDescription = "back button",
            tint = MaterialTheme.colors.onPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BackButtonPreview() {
    BackButton(
        navController = rememberNavController()
    )
}



