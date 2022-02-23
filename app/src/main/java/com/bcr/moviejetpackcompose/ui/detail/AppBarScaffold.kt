package com.bcr.moviejetpackcompose.ui.detail

import androidx.annotation.FloatRange
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.bcr.moviejetpackcompose.ui.components.AppImage
import com.bcr.moviejetpackcompose.ui.components.BackButton
import com.bcr.moviejetpackcompose.ui.components.MovieAppBar
import com.bcr.moviejetpackcompose.ui.theme.appTypography
import com.bcr.moviejetpackcompose.ui.theme.primaryBlack
import com.bcr.moviejetpackcompose.ui.theme.white
import com.bcr.moviejetpackcompose.utils.extensions.configureProgress
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState


@Composable
fun AppBarScaffold(
    url: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val collapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
    CollapsingToolbarScaffold(
        modifier = modifier,
        state = collapsingToolbarScaffoldState,
        scrollStrategy = ScrollStrategy.EnterAlways,
        toolbarModifier = Modifier.shadow(AppBarDefaults.TopAppBarElevation),
        toolbar = {
            val progress = collapsingToolbarScaffoldState.toolbarState.progress
            Surface(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .parallax(0.5f), // TODO: Affects performance
                color = primaryBlack,
                elevation = AppBarDefaults.TopAppBarElevation,
            ) {
                AppBarContent( progress = progress, url = url)
            }
            CollapsedAppBar(
                modifier = Modifier.clickable(onClick = { }),
                progress = progress,
                onBack = onBack,
            )
        },
        body = content
    )
}

@Composable
private fun CollapsedAppBar(
    modifier: Modifier = Modifier,
    @FloatRange(from = 0.0, to = 1.0) progress: Float,
    onBack: () -> Unit,
) {
    val progressReversed = 1f - progress
    MovieAppBar(
        modifier = modifier,
        title = {
            Text(
                modifier = Modifier.alpha(progressReversed.configureProgress(0.5f)),
                text = "Detail Movie",
                color = white,
                style = appTypography.body1
            )
        },
        navigationIcon = { BackButton(onClick = onBack) },
        actions = {}
    )
}

@Composable
private fun AppBarContent(
    modifier: Modifier = Modifier,
    url: String,
    @FloatRange(from = 0.0, to = 1.0) progress: Float,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .alpha(progress.configureProgress(0.5f)),
        contentAlignment = Alignment.Center
    ) {
        AppImage(
            url = url,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
    }
}
