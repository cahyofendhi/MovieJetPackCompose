package com.bcr.moviejetpackcompose.ui.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.bcr.moviejetpackcompose.ui.NavigationRoot
import com.bcr.moviejetpackcompose.ui.theme.appTypography

@Composable
fun HMovieCard(navController: NavController, isMovie: Boolean = true) {
    val width = LocalConfiguration.current.screenWidthDp
    Box(modifier = Modifier
        .padding(end = 16.dp)
        .wrapContentHeight()
        .width((width / 3).dp)
        .clickable {
            if (isMovie) {
                navController.navigate(NavigationRoot.MovieDetail.createRouteWithArguments(1))
            } else {
                navController.navigate(NavigationRoot.TVDetail.createRouteWithArguments(1))
            }
        }) {

        Column {
            Image(
                painter = rememberImagePainter(data ="https://gmedia.playstation.com/is/image/SIEPDC/spiderman-miles-morales-screenshot-04-disclaimer-en-01oct20?\$600px--t\$"),
                contentDescription = "Forest Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height((width / 3).dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Text(text = "Spider man Jack Sparrow",
                style = appTypography.body2,
                modifier = Modifier.padding(top = 16.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(text = "12/12/2021",
                style = appTypography.caption.copy(Color.DarkGray),
                modifier = Modifier.padding(top = 16.dp),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }

    }
}

@Composable
fun VMovieCard(navController: NavController, isMovie: Boolean = true) {
    val width = LocalConfiguration.current.screenWidthDp
    Box(modifier = Modifier
        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
        .wrapContentHeight()
        .clickable {
            if (isMovie) {
                navController.navigate(NavigationRoot.MovieDetail.createRouteWithArguments(1))
            } else {
                navController.navigate(NavigationRoot.TVDetail.createRouteWithArguments(1))
            }
        }) {

        Row {
            Image(
                painter = rememberImagePainter(data ="https://gmedia.playstation.com/is/image/SIEPDC/spiderman-miles-morales-screenshot-04-disclaimer-en-01oct20?\$600px--t\$"),
                contentDescription = "Forest Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width((width / 3 - 10).dp)
                    .height((width / 2.7).dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Column(modifier = Modifier
                .wrapContentHeight()
                .padding(start = 16.dp)) {

                Text(text = "Spider man Jack Sparrow",
                    style = appTypography.body2,
                    modifier = Modifier.padding(bottom = 8.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                LabelRating(rate = 8.5)

                Text(text = "Action, Comedy, Travel",
                    style = appTypography.caption.copy(Color.DarkGray),
                    modifier = Modifier.padding(top = 5.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

            }

        }

    }
}

@Composable
fun LabelRating(rate: Double) {
    Box(modifier = Modifier
        .wrapContentHeight()
        .wrapContentWidth()
        .background(Color.Magenta, RoundedCornerShape(5.dp)),
    ) {
        Text(text = "$rate",
            style = TextStyle(
                color = Color.White,
                fontSize = 12.sp),
            modifier = Modifier.padding(vertical = 3.dp, horizontal = 5.dp),
            maxLines = 1,
        )
    }
}

@Composable
fun CreditPeopleCard() {
    val width = LocalConfiguration.current.screenWidthDp
    val imageSize = width / 5
    Box(modifier = Modifier
        .padding(end = 8.dp)
        .width(imageSize.dp)
        .wrapContentHeight()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberImagePainter(data ="https://images.unsplash.com/photo-1516756587022-7891ad56a8cd?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=187&q=80"),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageSize.dp)
                    .clip(CircleShape)
            )
            
            Text(text = "Micheal wyan Micheal wyan Micheal wyan",
                style = appTypography.caption,
                maxLines = 2,
                modifier = Modifier.padding(top = 8.dp),
                overflow = TextOverflow.Ellipsis)
            
        }
    }
}

@Composable
fun SimiliarMovieCard(width: Int, navController: NavController, isMovie: Boolean = true) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                if (isMovie) {
                    navController.navigate(NavigationRoot.MovieDetail.createRouteWithArguments(1))
                } else {
                    navController.navigate(NavigationRoot.TVDetail.createRouteWithArguments(1))
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberImagePainter(data ="https://images.unsplash.com/photo-1516756587022-7891ad56a8cd?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=187&q=80"),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height((width / 4).dp)
                .clip(RoundedCornerShape(5.dp))
        )

        Text(text = "Micheal wyan Micheal wyan Micheal wyan",
            style = appTypography.caption,
            maxLines = 2,
            modifier = Modifier.padding(top = 8.dp),
            overflow = TextOverflow.Ellipsis)

    }
}