package com.bcr.moviejetpackcompose.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.bcr.moviejetpackcompose.ui.theme.gray

@Composable
fun AppImage(url: String, modifier: Modifier) {
    Image(
        painter = rememberImagePainter(url),
        placeholder = { Placeholder(modifier = modifier) },
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}

@Composable
private fun Placeholder(modifier: Modifier) {
    Card(
        modifier = modifier,
        backgroundColor = gray
    ) {
        Box(modifier = Modifier
            .background(gray, shape = RoundedCornerShape(10.dp))
            .fillMaxSize())
    }
}

@Composable
private fun Image(
    painter: ImagePainter,
    placeholder: @Composable () -> Unit,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
) {
    Box(modifier) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter,
            modifier = Modifier.matchParentSize()
        )

        AnimatedVisibility(
            visible = when (painter.state) {
                is ImagePainter.State.Empty,
                -> true
                is ImagePainter.State.Success,
                -> false
                is ImagePainter.State.Loading,
                -> true
                is ImagePainter.State.Error,
                -> true
            }
        ) { placeholder() }
    }
}