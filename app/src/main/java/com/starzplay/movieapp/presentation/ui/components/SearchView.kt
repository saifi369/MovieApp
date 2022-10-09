package com.starzplay.movieapp.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.starzplay.movieapp.presentation.ui.theme.MovieAppTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchView(modifier: Modifier = Modifier, onImeAction: (String) -> Unit) {

    var searchText by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = searchText,
        onValueChange = { newValue ->
            searchText = newValue
        },
        label = { Text("Search") },
        trailingIcon = {
            Icon(
                modifier = Modifier
                    .clickable {
                        keyboardController?.hide()
                        onImeAction(searchText)
                    },
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(onSearch = {
            keyboardController?.hide()
            onImeAction(searchText)
        })
    )
}

@Preview
@Composable
fun PreviewSearchView() {
    MovieAppTheme {
        SearchView(onImeAction = {})
    }
}