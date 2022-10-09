package com.starzplay.movieapp.presentation.ui.playerscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.starzplay.movieapp.R
import com.starzplay.movieapp.domain.model.VideoItem
import com.starzplay.movieapp.presentation.ui.theme.MovieAppTheme

class PlayerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val videoItem = intent.getParcelableExtra<VideoItem>("video_item")
        setContent {
            MovieAppTheme {
                ScreenContent {
                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(data = videoItem?.fullBackdropUrl)
                            .size(Size.ORIGINAL)
                            .placeholder(R.drawable.placeholder_backdrop)
                            .error(R.drawable.placeholder_backdrop)
                            .crossfade(500)
                            .build()
                    )

                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painter,
                        contentScale = ContentScale.FillWidth,
                        contentDescription = "Backdrop image"
                    )
                }
            }
        }
    }
}

@Composable
private fun ScreenContent(content: @Composable () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        content()
    }
}