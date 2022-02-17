package com.bcr.moviejetpackcompose.ui.detail

import androidx.annotation.FloatRange
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bcr.moviejetpackcompose.ui.components.AppImage
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
    navController: NavHostController,
    url: String,
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
                navController = navController,
                modifier = Modifier.clickable(onClick = { }),
                progress = progress,
            )
        },
        body = content
    )
}

@Composable
fun BackButton(navController: NavHostController) {
    Icon(
        Icons.Outlined.ArrowBack,
        contentDescription = "back",
        tint = Color.White,
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .height(25.dp)
            .width(25.dp)
            .clickable { navController.popBackStack() }
    )
}

@Composable
private fun CollapsedAppBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    @FloatRange(from = 0.0, to = 1.0) progress: Float,
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
        navigationIcon = { BackButton(navController = navController) },
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
