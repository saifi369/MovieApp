package com.starzplay.movieapp.presentation.ui.videodetail

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.starzplay.movieapp.R
import com.starzplay.movieapp.domain.model.VideoItem
import com.starzplay.movieapp.domain.toPersonItem
import com.starzplay.movieapp.presentation.ui.components.CastItem
import com.starzplay.movieapp.presentation.ui.components.SubtitleText
import com.starzplay.movieapp.presentation.ui.components.SubtitleText2
import com.starzplay.movieapp.presentation.ui.destinations.ArtistDetailScreenDestination
import com.starzplay.movieapp.presentation.ui.navgraph.MainNavGraph
import com.starzplay.movieapp.presentation.ui.playerscreen.PlayerActivity
import com.starzplay.movieapp.presentation.ui.theme.subTitlePrimary2

@MainNavGraph
@Destination
@Composable
fun VideoDetailScreen(navigator: DestinationsNavigator, videoItem: VideoItem) {

    val viewModel: VideoDetailVM = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()
    val castInfoState by viewModel.castDetail.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data = videoItem.fullBackdropUrl)
                    .size(Size.ORIGINAL)
                    .placeholder(R.drawable.placeholder_backdrop)
                    .error(R.drawable.placeholder_backdrop)
                    .crossfade(500)
                    .build()
            )
            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                painter = painter,
                contentScale = ContentScale.FillWidth,
                contentDescription = "Backdrop image"
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = videoItem.title!!,
                    modifier = Modifier.padding(top = 10.dp),
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.W700,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp, top = 10.dp)
                ) {

                    Column(Modifier.weight(1f)) {
                        SubtitleText(
                            text = stringResource(R.string.language),
                        )
                        SubtitleText2(
                            text = videoItem.originalLanguage!!,
                        )
                    }
                    Column(Modifier.weight(1f)) {
                        SubtitleText(
                            text = stringResource(R.string.rating)
                        )
                        SubtitleText2(
                            text = videoItem.voteAverage.toString(),
                        )
                    }
                    Column(Modifier.weight(1f)) {
                        SubtitleText(
                            text = stringResource(R.string.vote_count)
                        )
                        SubtitleText2(
                            text = "(${videoItem.voteCount.toString()})",
                        )
                    }
                    Column(Modifier.weight(1f)) {
                        SubtitleText(
                            text = stringResource(R.string.release_date)
                        )
                        SubtitleText2(
                            text = videoItem.releaseDate!!
                        )
                    }
                }
                val context = LocalContext.current
                Button(modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        Intent(context, PlayerActivity::class.java).apply {
                            putExtra("video_item", videoItem)
                            context.startActivity(this)
                        }
                    }) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play button")
                }
                Text(
                    text = stringResource(R.string.description),
                    style = MaterialTheme.typography.subTitlePrimary2,
                    color = MaterialTheme.colors.onBackground
                )
                Text(
                    text = videoItem.overview!!,
                    color = Color.Gray,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                if (viewState.isSuccess && castInfoState.isNotEmpty()) {
                    //Show Cast list
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(castInfoState) { cast ->
                            CastItem(cast) {
                                navigator.navigate(ArtistDetailScreenDestination(personItem = cast.toPersonItem()))
                            }
                        }
                    }
                } else if (viewState.error.isEmpty() && viewState.isLoading.not())
                    viewModel.getCastDetails(videoItem.mediaType!!, videoItem.id!!)
            }
        }
    }
}