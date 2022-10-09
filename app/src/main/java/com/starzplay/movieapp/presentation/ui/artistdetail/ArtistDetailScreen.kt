package com.starzplay.movieapp.presentation.ui.artistdetail

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import com.starzplay.movieapp.domain.model.PersonItem
import com.starzplay.movieapp.domain.model.VideoItem
import com.starzplay.movieapp.presentation.ui.components.SubtitleText
import com.starzplay.movieapp.presentation.ui.destinations.VideoDetailScreenDestination
import com.starzplay.movieapp.presentation.ui.home.ShowMediaList
import com.starzplay.movieapp.presentation.ui.navgraph.MainNavGraph
import com.starzplay.movieapp.presentation.ui.theme.cornerRadius10

@MainNavGraph
@Destination
@Composable
fun ArtistDetailScreen(navigator: DestinationsNavigator, personItem: PersonItem) {

    val viewModel: ArtistDetailVM = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 8.dp, top = 8.dp, end = 8.dp)
    ) {
        Row {

            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data = personItem.fullPosterUrl)
                    .size(Size.ORIGINAL)
                    .placeholder(R.drawable.placeholder_backdrop)
                    .error(R.drawable.placeholder_backdrop)
                    .crossfade(500)
                    .build()
            )
            Image(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .cornerRadius10()
                    .height(300.dp)
                    .width(220.dp),
                painter = painter,
                contentScale = ContentScale.FillBounds,
                contentDescription = "Backdrop image"
            )
            Column {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = personItem.name!!,
                    color = MaterialTheme.colors.primary,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Medium
                )
                PersonalInfo(stringResource(R.string.know_for), personItem.knownForDepartment!!)
                PersonalInfo(stringResource(R.string.gender), personItem.gender!!.genderInString())
                PersonalInfo(stringResource(R.string.popularity), personItem.popularity.toString())
                if (viewState.isSuccess) {
                    val personDetail by viewModel.personDetail.collectAsState()
                    if (personDetail.birthday?.isNotEmpty() == true)
                        PersonalInfo(stringResource(R.string.birth_day), personDetail.birthday!!)
                    if (personDetail.placeOfBirth?.isNotEmpty() == true)
                        PersonalInfo(
                            stringResource(R.string.place_of_birth),
                            personDetail.placeOfBirth!!
                        )
                }
            }
        }

        if (viewState.isSuccess) {
            val personDetail by viewModel.personDetail.collectAsState()
            Crossfade(targetState = personDetail) {
                if (personDetail.biography?.isNotEmpty() == true)
                    SubtitleText(text = personDetail.biography!!)
                else
                    SubtitleText(text = "Biography not available")
            }
        } else {
            Button(modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.getPersonDetails(personItem.id!!) }) {
                Text(text = "Load Biography")
            }
        }

        personItem.knownFor?.let {
            if (it.isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = stringResource(R.string.know_for),
                    color = Color.Gray,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium
                )
                ShowMediaList(it) { mediaItem ->
                    navigator.navigate(VideoDetailScreenDestination(videoItem = mediaItem as VideoItem))
                }
            }
        }
    }
}

@Composable
fun PersonalInfo(title: String, info: String) {
    Column(modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)) {
        Text(
            text = title,
            color = Color.Gray,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = info,
            color = MaterialTheme.colors.primary,
            fontSize = 16.sp
        )
    }
}


private fun Int.genderInString(): String {
    return when (this) {
        1 -> "Female"
        2 -> "Male"
        else -> ""
    }
}