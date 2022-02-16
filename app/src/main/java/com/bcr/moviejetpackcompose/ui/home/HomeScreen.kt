package com.bcr.moviejetpackcompose.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.bcr.moviejetpackcompose.core.viewmodels.HomeViewModel
import com.bcr.moviejetpackcompose.ui.NavigationRoot
import com.bcr.moviejetpackcompose.ui.card.HMovieCard
import com.bcr.moviejetpackcompose.ui.card.VMovieCard
import com.bcr.moviejetpackcompose.ui.theme.appTypography
import com.bcr.moviejetpackcompose.utils.getMovieImage
import com.google.accompanist.pager.ExperimentalPagerApi

private lateinit var appNavController: NavHostController
private lateinit var viewmodel: HomeViewModel

@ExperimentalPagerApi
@Composable
fun HomeScreen(navController: NavHostController?, viewModel: HomeViewModel) {
    viewmodel = viewModel
    navController?.let { appNavController = it }
    Scaffold() {
        Column {
            AppBarHome()
            ContentHome()
        }
    }
}

@Composable
fun AppBarHome() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(58.dp)
            .background(Color.White),) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                "Movie",
                style = appTypography.h6,
                maxLines = 2,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
            )

            Icon(
                Icons.Outlined.Search,
                contentDescription = "Search",
                tint = Color.Black,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .wrapContentWidth(Alignment.End)
            )
        }

    }
}

@ExperimentalPagerApi
@Composable
fun ContentHome() {
    LazyColumn(modifier = Modifier
        .background(Color.White)
        .fillMaxSize()) {
        item { UpcomingHome() }
        item { PopularMovieHome() }

        item {
            Spacer(modifier = Modifier.padding(16.dp))
        }

        item {
            Text(text = "Top Movie",
                modifier = Modifier.padding(16.dp),
                style = appTypography.body2.copy(fontWeight = FontWeight.Bold))
        }

        itemsIndexed(viewmodel.topMovies) { index, item ->
            VMovieCard(appNavController, item)
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@ExperimentalPagerApi
@Composable
fun UpcomingHome() {
    val width = LocalConfiguration.current.screenWidthDp / 2.5

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height((width / 1.5).dp)
            .padding(vertical = 14.dp),
        contentPadding = PaddingValues(horizontal = 14.dp)
    ) {
        itemsIndexed(viewmodel.upcomingMovies){ index, item ->
            Image(
                painter = rememberImagePainter(data = item.getImageBackdrop()),
                contentDescription = "Forest Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .fillMaxHeight()
                    .width(width.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        appNavController.navigate(
                            NavigationRoot.MovieDetail.createRouteWithArguments(item)
                        )
                    }
            )
        }
    }
}

@Composable
fun PopularMovieHome() {
    Column {
        Text(text = "Popular Movie",
            modifier = Modifier.padding(16.dp),
            style = appTypography.body2.copy(fontWeight = FontWeight.Bold))
       
        LazyRow(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp)) {

            itemsIndexed(viewmodel.popularMovies) { _, item ->
                HMovieCard(navController = appNavController, movie = item)
            }

        }
        
    }
}

@Preview(showBackground = true)
@ExperimentalPagerApi
@Composable
fun HomeScreenPreview() {
    HomeScreen(null, HomeViewModel())
}