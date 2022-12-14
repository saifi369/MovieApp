package com.starzplay.movieapp.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.starzplay.movieapp.presentation.ui.theme.MovieAppTheme
import com.starzplay.movieapp.presentation.ui.theme.categoryText
import com.starzplay.movieapp.presentation.ui.theme.subTitlePrimary
import com.starzplay.movieapp.presentation.ui.theme.subTitlePrimary2

@Composable
fun CategoryText(text: String) {
    Text(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        maxLines = 1,
        text = text,
        style = MaterialTheme.typography.categoryText
    )
}

@Composable
fun SubtitleText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.subTitlePrimary,
        color = MaterialTheme.colors.onBackground
    )
}

@Composable
fun SubtitleText2(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.subTitlePrimary2
    )
}

@Preview
@Composable
fun PreviewCategoryText() {
    MovieAppTheme {
        CategoryText(text = "Movie Name")
    }
}