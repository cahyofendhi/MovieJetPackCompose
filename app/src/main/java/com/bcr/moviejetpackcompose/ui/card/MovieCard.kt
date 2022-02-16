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
import com.bcr.moviejetpackcompose.core.model.Crew
import com.bcr.moviejetpackcompose.core.model.Movie
import com.bcr.moviejetpackcompose.ui.NavigationRoot
import com.bcr.moviejetpackcompose.ui.theme.*

@Composable
fun HMovieCard(navController: NavController, movie: Movie, isMovie: Boolean = true) {
    val width = LocalConfiguration.current.screenWidthDp
    Box(modifier = Modifier
        .padding(end = 16.dp)
        .wrapContentHeight()
        .width((width / 3).dp)
        .clickable {
            if (isMovie) {
                navController.navigate(NavigationRoot.MovieDetail.createRouteWithArguments(movie))
            } else {
                navController.navigate(NavigationRoot.TVDetail.createRouteWithArguments(movie))
            }
        }) {

        Column {
            Image(
                painter = rememberImagePainter(data = movie.getImageBackdrop()),
                contentDescription = "Forest Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height((width / 3).dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Text(text = movie.getTitleMovie(),
                style = appTypography.body2,
                modifier = Modifier.padding(top = 16.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(text = movie.getDateMovie(),
                style = appTypography.caption.copy(Color.DarkGray),
                modifier = Modifier.padding(top = 16.dp),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }

    }
}

@Composable
fun VMovieCard(navController: NavController, movie: Movie, isMovie: Boolean = true) {
    val width = LocalConfiguration.current.screenWidthDp
    Box(modifier = Modifier
        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
        .wrapContentHeight()
        .clickable {
            if (isMovie) {
                navController.navigate(NavigationRoot.MovieDetail.createRouteWithArguments(movie))
            } else {
                navController.navigate(NavigationRoot.TVDetail.createRouteWithArguments(movie))
            }
        }) {

        Row {
            Image(
                painter = rememberImagePainter(data = movie.getImageBackdrop()),
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

                Text(text = movie.getTitleMovie(),
                    style = appTypography.body2,
                    modifier = Modifier.padding(bottom = 12.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                movie.voteAverage?.let { LabelRating(rate = it) }

                Text(text = movie.genreList(),
                    style = appTypography.body2.copy(Color.DarkGray),
                    modifier = Modifier.padding(top = 12.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

            }

        }

    }
}

@Composable
fun LabelRating(rate: Double) {
    var bgColor = green
    if (rate < 7.0) {
        bgColor = red
    } else if (rate < 8) {
        bgColor = yellow
    }
    Box(modifier = Modifier
        .wrapContentHeight()
        .wrapContentWidth()
        .background(bgColor, RoundedCornerShape(5.dp)),
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
fun CreditPeopleCard(crew: Crew) {
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
                painter = rememberImagePainter(data = crew.getImageProfile()),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageSize.dp)
                    .clip(CircleShape)
            )
            
            Text(text = crew.name!!,
                style = appTypography.caption,
                maxLines = 2,
                modifier = Modifier.padding(top = 8.dp),
                overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun SimiliarMovieCard(width: Int, navController: NavController, movie: Movie, isMovie: Boolean = true) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                if (isMovie) {
                    navController.navigate(NavigationRoot.MovieDetail.createRouteWithArguments(movie))
                } else {
                    navController.navigate(NavigationRoot.TVDetail.createRouteWithArguments(movie))
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberImagePainter(data = movie.getImageBackdrop()),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height((width / 4).dp)
                .clip(RoundedCornerShape(5.dp))
        )

        Text(text = movie.getTitleMovie(),
            style = appTypography.caption,
            maxLines = 2,
            modifier = Modifier.padding(top = 8.dp),
            overflow = TextOverflow.Ellipsis)

    }
}