package com.bcr.moviejetpackcompose.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bcr.moviejetpackcompose.ui.theme.primaryBlack

@Composable
fun MovieAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit,
) {
    TopAppBar(
        modifier = modifier.height(56.dp),
        title = title,
        navigationIcon = navigationIcon,
        actions = actions,
        backgroundColor = Color.Transparent,
        contentColor = contentColorFor(primaryBlack),
        elevation = 0.dp
    )
}
