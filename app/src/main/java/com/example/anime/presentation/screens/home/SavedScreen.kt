package com.example.anime.presentation.screens.home

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.anime.RequestPermission
import com.example.anime.presentation.navigation.MainScreenRoute
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ShowImages(navHostController: NavHostController) {

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            val data = Uri.encode(uri.toString())
            navHostController.navigate(MainScreenRoute.Gallery.route.plus("/$data"))
        }

    Box(modifier = Modifier.fillMaxSize()) {

        RequestPermission(permission = Manifest.permission.READ_EXTERNAL_STORAGE)


        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp),
            onClick = {
                launcher.launch("image/*")
            },
            backgroundColor = MaterialTheme.colors.onPrimary,
            contentColor = MaterialTheme.colors.primary
        ) {
            Icon(imageVector = Icons.Rounded.Add, contentDescription = "add photo")
        }


    }


}

@Preview
@Composable
fun ShowImagesPreview() {
    ShowImages(navHostController = rememberNavController())
}