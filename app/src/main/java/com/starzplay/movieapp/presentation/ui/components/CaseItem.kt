package com.starzplay.movieapp.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.starzplay.movieapp.R
import com.starzplay.movieapp.domain.model.CastInfo

@Composable
fun CastItem(castInfo: CastInfo, onItemClick: (CastInfo) -> Unit) {
    Column(
        modifier = Modifier
            .size(height = 120.dp, width = 100.dp)
            .clickable {
                onItemClick(castInfo)
            }, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current).data(data = castInfo.fullProfileUrl)
                .placeholder(R.drawable.placeholder).error(R.drawable.placeholder).crossfade(500)
                .build()
        )

        Image(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            painter = painter,
            contentScale = ContentScale.Crop,
            contentDescription = "Poster image"
        )

        SubtitleText2(text = castInfo.name!!)
    }
}

@Preview(showBackground = false, device = Devices.PIXEL_4)
@Composable
fun PreviewCastItem() {

}