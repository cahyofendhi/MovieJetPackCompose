package com.bcr.moviejetpackcompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.flowlayout.FlowRow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bcr.moviejetpackcompose.ui.theme.appTypography
import com.bcr.moviejetpackcompose.ui.theme.primaryBlack
import com.bcr.moviejetpackcompose.ui.theme.white
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode

@Composable
fun TagLabel(tags: List<String>, backgroundColor: Color = white, borderColor: Color = primaryBlack) {
    FlowRow(
        modifier = Modifier.padding(8.dp),
        mainAxisAlignment = MainAxisAlignment.Start,
        mainAxisSize = SizeMode.Expand,
        crossAxisSpacing = 12.dp,
        mainAxisSpacing = 8.dp
    ) {
        tags.forEach { it ->
            Text(text = it, style = appTypography.caption,
                modifier = Modifier.background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(10.dp)
                )
                    .border(1.dp, color = borderColor, shape = RoundedCornerShape(10.dp))
                    .padding(vertical = 5.dp, horizontal = 8.dp),
            maxLines = 1)
        }

    }
}