package com.bcr.moviejetpackcompose.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bcr.moviejetpackcompose.ui.theme.white

@Composable
fun BackButton(onClick:() -> Unit, color: Color = white) {
    IconButton(onClick) {
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            tint = color,
            contentDescription = "back",
        )
    }
}

@Composable
fun SearchButton(onClick: () -> Unit) {
    IconButton(onClick) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = "Search",
            tint = Color.Black,
        )
    }
}