package com.bcr.moviejetpackcompose.ui.components

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.bcr.moviejetpackcompose.ui.theme.white


@Composable
fun AppScaffold(content: @Composable () -> Unit) {
    Scaffold(
        backgroundColor = white,
        contentColor = white
    ) {
        content()
    }
}