package com.bcr.moviejetpackcompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bcr.moviejetpackcompose.ui.theme.*

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

@Composable
fun AppButton(title: String,
              enable: Boolean = false,
              onClick: () -> Unit) {
    Button(
        onClick = if (enable) { onClick } else { {} },
        colors = ButtonDefaults
            .buttonColors(backgroundColor = if (enable) { primaryBlack} else { secondGray }),
        shape = RoundedCornerShape(size = 10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
    ) {
        Text(
            text = title,
            style = appTypography.button.copy(white)
        )
    }
}