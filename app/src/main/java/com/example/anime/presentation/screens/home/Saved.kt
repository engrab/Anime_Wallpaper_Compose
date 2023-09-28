package com.example.anime.presentation.screens.home

import android.Manifest
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ShowImages(navHostController: NavHostController) {
    
    val launch = rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)
    
    Button(onClick = {
        if (!launch.status.isGranted){
            launch.launchPermissionRequest()
        }
    }) {
        
        Text(text = "Read Permission")
    }
}