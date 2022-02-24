package com.bcr.moviejetpackcompose.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.bcr.moviejetpackcompose.ui.theme.primaryBlack

@Composable
fun AppIcon(imageVector: ImageVector) {
    Icon(
        imageVector,
        contentDescription = "",
        modifier = Modifier
            .padding(15.dp)
            .size(24.dp),
        tint = primaryBlack
    )
}