package com.starzplay.movieapp.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.starzplay.movieapp.domain.model.MediaItem
import com.starzplay.movieapp.domain.model.PersonItem
import com.starzplay.movieapp.domain.model.VideoItem
import com.starzplay.movieapp.ui.navgraph.MainNavGraph

@MainNavGraph
@Destination
@Composable
fun DetailScreen(navigator: DestinationsNavigator, mediaItem: MediaItem) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (mediaItem) {
            is VideoItem -> {
                VideoItemContent(mediaItem)
            }
            is PersonItem -> {
                PersonItemContent(mediaItem)
            }
        }
    }
}

@Composable
private fun VideoItemContent(mediaItem: VideoItem) {
    Text(text = "Detail screen--${mediaItem.title}")
}

@Composable
private fun PersonItemContent(mediaItem: PersonItem) {
    Text(text = "Detail screen--${mediaItem.name}")
}