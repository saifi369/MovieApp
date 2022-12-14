package com.starzplay.movieapp.presentation.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.starzplay.movieapp.R
import com.starzplay.movieapp.domain.model.MediaItem
import com.starzplay.movieapp.domain.model.PersonItem
import com.starzplay.movieapp.domain.model.VideoItem
import com.starzplay.movieapp.presentation.ui.components.CategoryText
import com.starzplay.movieapp.presentation.ui.components.LottieView
import com.starzplay.movieapp.presentation.ui.components.MovieItem
import com.starzplay.movieapp.presentation.ui.components.SearchView
import com.starzplay.movieapp.presentation.ui.destinations.ArtistDetailScreenDestination
import com.starzplay.movieapp.presentation.ui.destinations.VideoDetailScreenDestination
import com.starzplay.movieapp.presentation.ui.navgraph.MainNavGraph

@MainNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator) {

    val viewModel: MovieVM = hiltViewModel()

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxSize()
    ) {
        val context = LocalContext.current
        SearchView {
            if (it.isEmpty()) Toast.makeText(context, "Please enter some text", Toast.LENGTH_LONG)
                .show()
            else viewModel.performSearch(query = it)
        }

        Column {
            val viewState by viewModel.viewState.collectAsState()
            if (viewState.isLoading) {
                //loading results
                LottieView(
                    modifier = Modifier
                        .weight(0.5f, fill = false)
                        .align(CenterHorizontally),
                    fileName = R.raw.searching
                )
                Text(
                    modifier = Modifier
                        .weight(0.5f, fill = false)
                        .align(CenterHorizontally),
                    color = MaterialTheme.colors.primary,
                    text = "Searching..."
                )
            } else if (viewState.error.isNotEmpty()) {
                //some error occurred
                LottieView(
                    modifier = Modifier
                        .weight(.5f, fill = false)
                        .align(CenterHorizontally),
                    fileName = R.raw.error
                )
                Text(
                    modifier = Modifier
                        .weight(.5f, fill = false)
                        .align(CenterHorizontally),
                    color = MaterialTheme.colors.primary,
                    text = viewState.error
                )

            } else if (viewState.isSuccess) {
                //results loaded
                val moviesList by viewModel.movieList.collectAsState()
                val tvList by viewModel.tvList.collectAsState()
                val personList by viewModel.personList.collectAsState()

                if ((moviesList + tvList + personList).isEmpty()) {
                    LottieView(
                        modifier = Modifier
                            .weight(.5f, fill = false)
                            .align(CenterHorizontally),
                        fileName = R.raw.empty_box
                    )
                    OutlinedButton(modifier = Modifier
                        .weight(.5f, fill = false)
                        .wrapContentHeight()
                        .align(CenterHorizontally),
                        onClick = { viewModel.performSearch("", isUsingCache = true) }) {
                        Text("Oops! Nothing found, Load old data?")
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .verticalScroll(rememberScrollState()),
                    ) {
                        //Movies List
                        if (moviesList.isNotEmpty()) {
                            CategoryText(text = "Movies")
                            ShowMediaList(moviesList) {
                                navigator.navigate(VideoDetailScreenDestination(videoItem = it as VideoItem))
                            }
                        }
                        //Tv List
                        if (tvList.isNotEmpty()) {
                            CategoryText(text = "Shows")
                            ShowMediaList(tvList) {
                                navigator.navigate(VideoDetailScreenDestination(videoItem = it as VideoItem))
                            }
                        }
                        //Persons List
                        if (personList.isNotEmpty()) {
                            CategoryText(text = "Artists")
                            ShowMediaList(personList) {
                                navigator.navigate(
                                    ArtistDetailScreenDestination(
                                        personItem = it as PersonItem
                                    )
                                )
                            }
                        }
                    }
                }
            } else {
                LottieView(
                    modifier = Modifier
                        .weight(0.5f, fill = false)
                        .align(CenterHorizontally),
                    fileName = R.raw.empty_box
                )
                Text(
                    modifier = Modifier
                        .weight(.5f, fill = false)
                        .align(CenterHorizontally),
                    color = MaterialTheme.colors.primary,
                    text = "Nothing to show, Search something"
                )
            }
        }
    }
}

@Composable
fun ShowMediaList(mediaList: List<MediaItem?>, onItemClick: (MediaItem) -> Unit) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(mediaList) { media ->
            media?.let {
                MovieItem(media) {
                    onItemClick(it)
                }
            }
        }
    }
}