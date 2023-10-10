package com.example.anime.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomMenu(
    modifier: Modifier,
    isLiked: Boolean,

    onHome: () -> Unit,
    onDownload: () -> Unit,
    onLock: () -> Unit,
    onShare: () -> Unit,
    onFavourite: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
        backgroundColor = MaterialTheme.colors.primary,
        shape = RectangleShape
    ) {
        Column(Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = onDownload) {
                    Icon(
                        imageVector = Icons.Rounded.Download,
                        contentDescription = "download"
                    )
                }
                IconButton(onClick = onFavourite) {
                    Icon(
                        imageVector = if (isLiked) (Icons.Rounded.Favorite) else Icons.Rounded.FavoriteBorder,
                        contentDescription = "favourite"
                    )
                }
                IconButton(onClick = onShare) {
                    Icon(
                        imageVector = Icons.Rounded.Share,
                        contentDescription = "share"
                    )
                }
                IconButton(onClick = onLock) {
                    Icon(
                        imageVector = Icons.Rounded.LockOpen,
                        contentDescription = "Lock"
                    )
                }
                IconButton(onClick = onHome) {
                    Icon(
                        imageVector = Icons.Rounded.Wallpaper,
                        contentDescription = "Home"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun BottomMenuPreview() {
    BottomMenu(
        modifier = Modifier,
        isLiked = true,
        onHome = {  },
        onDownload = {  },
        onLock = {  },
        onShare = {  },
        onFavourite = {  })
}
