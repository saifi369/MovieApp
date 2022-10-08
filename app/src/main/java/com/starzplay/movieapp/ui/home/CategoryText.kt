package com.starzplay.movieapp.ui.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.starzplay.movieapp.ui.theme.MovieAppTheme
import com.starzplay.movieapp.ui.theme.Typography
import com.starzplay.movieapp.ui.theme.categoryText
import com.starzplay.movieapp.ui.theme.subTitlePrimary

@Composable
fun CategoryText(text: String) {
    Text(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        maxLines = 1,
        text = text,
        style = Typography.categoryText
    )
}

@Composable
fun SubtitleText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.subTitlePrimary
    )
}

@Composable
fun SubtitleText2(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colors.primary,
        fontSize = 17.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Preview
@Composable
fun PreviewCategoryText() {
    MovieAppTheme {
        CategoryText(text = "Movie Name")
    }
}