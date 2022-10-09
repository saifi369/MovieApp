package com.starzplay.movieapp.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.starzplay.movieapp.R
import com.starzplay.movieapp.domain.model.MediaItem
import com.starzplay.movieapp.domain.model.PersonItem
import com.starzplay.movieapp.domain.model.VideoItem
import com.starzplay.movieapp.presentation.ui.theme.cornerRadius10
import com.starzplay.movieapp.presentation.ui.theme.subTitlePrimary

@Composable
fun MovieItem(mediaItem: MediaItem, onItemClick: (MediaItem) -> Unit) {

    Column(
        modifier = Modifier
            .size(height = 250.dp, width = 160.dp)
            .clickable {
                onItemClick(mediaItem)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PosterImage(width = 160.dp, height = 220.dp, imageUrl = mediaItem.fullPosterUrl ?: "")
        when (mediaItem) {
            is VideoItem -> {
                mediaItem.title?.let {
                    MediaTitle(it)
                }
            }
            is PersonItem -> {
                mediaItem.name?.let {
                    MediaTitle(it)
                }
            }
        }
    }
}

@Composable
private fun MediaTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.subTitlePrimary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun PosterImage(height: Dp, width: Dp, imageUrl: String) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data = imageUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .crossfade(500)
            .build()
    )

    Image(
        modifier = Modifier
            .size(height = height, width = width)
            .cornerRadius10(),
        painter = painter,
        contentScale = ContentScale.Crop,
        contentDescription = "Poster image"
    )
}

@Preview(showBackground = false, device = Devices.PIXEL_4)
@Composable
fun PreviewMovieItem() {
    MovieItem(mediaItem = VideoItem(mediaType = "movie", title = "Gravity")) {}
}