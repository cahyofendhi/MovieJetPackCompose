package com.bcr.moviejetpackcompose.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bcr.moviejetpackcompose.core.model.Crew
import com.bcr.moviejetpackcompose.ui.card.LabelRating
import com.bcr.moviejetpackcompose.ui.theme.appTypography
import com.bcr.moviejetpackcompose.ui.theme.gray

val ShimmerColorShades = listOf(
    gray.copy(0.9f),
    gray.copy(0.2f),
    gray.copy(0.9f)
)

@Composable
fun ShimmerAnimation(horizontal: Boolean = true, height: Double = 100.0, ratio: Double = 2.5) {
    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )
    val brush = Brush.linearGradient(
        colors = ShimmerColorShades,
        start = Offset(10f, 10f),
        end = Offset(translateAnim.value, translateAnim.value)
    )
    if (horizontal) {
        HShimmerItem(brush = brush, height, ratio)
    } else {
        VShimmerItem(brush = brush)
    }
}


@Composable
fun ShimmerAnimationCard(content: @Composable (Brush) -> Unit) {
    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )
    val brush = Brush.linearGradient(
        colors = ShimmerColorShades,
        start = Offset(10f, 10f),
        end = Offset(translateAnim.value, translateAnim.value)
    )
    content(brush)
}

@Composable
fun HShimmerItem( brush: Brush, height: Double = 30.0, ratio: Double = 2.5) {
    val width = LocalConfiguration.current.screenWidthDp / ratio
    Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)) {
        Spacer(
            modifier = Modifier
                .width(width.dp)
                .size(height.dp)
                .background(brush = brush, shape = RoundedCornerShape(10.dp))
        )
        Spacer(
            modifier = Modifier
                .width(width.dp)
                .height(15.dp)
                .padding(top = 8.dp)
                .background(brush = brush, shape = RoundedCornerShape(10.dp))
        )
    }
}

@Composable
fun VShimmerItem(brush: Brush) {
    val width = LocalConfiguration.current.screenWidthDp
    Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Spacer(modifier = Modifier
            .width((width / 3 - 10).dp)
            .height((width / 2.7).dp)
            .background(brush = brush, shape = RoundedCornerShape(10.dp))
        )

        Column(modifier = Modifier
            .wrapContentHeight()
            .padding(start = 16.dp)) {

            Spacer(
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .width(200.dp)
                    .height(15.dp)
                    .background(brush = brush, shape = RoundedCornerShape(10.dp))
            )

            Spacer(
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .width(100.dp)
                    .height(15.dp)
                    .background(brush = brush, shape = RoundedCornerShape(10.dp))
            )

        }

    }
}

@Composable
fun PeopleShimmerItem() {
    val width = LocalConfiguration.current.screenWidthDp
    val imageSize = width / 5
    ShimmerAnimationCard { brush ->
        Box(
            modifier = Modifier
                .padding(end = 8.dp)
                .width(imageSize.dp)
                .wrapContentHeight()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(imageSize.dp)
                        .background(brush = brush, shape = CircleShape)
                )

                Spacer(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 12.dp)
                        .width(50.dp)
                        .height(10.dp)
                        .background(brush = brush, shape = RoundedCornerShape(10.dp))
                )
            }
        }
    }
}

@Composable
fun SimiliarShimmerItem() {
    val width = LocalConfiguration.current.screenWidthDp
    val imageSize = width / 4
    ShimmerAnimationCard { brush ->
        Box(
            modifier = Modifier
                .padding(end = 8.dp)
                .width(imageSize.dp)
                .wrapContentHeight()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(imageSize.dp)
                        .background(brush = brush, shape = RoundedCornerShape(5.dp))
                )

                Spacer(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 12.dp)
                        .width(50.dp)
                        .height(10.dp)
                        .background(brush = brush, shape = RoundedCornerShape(10.dp))
                )
            }
        }
    }
}

@Composable
fun HShimmerAnimation() {
    LazyRow(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp)) {

        repeat(7) {
            item {
                ShimmerAnimation(true, 120.0, 3.0)
            }
        }

    }
}