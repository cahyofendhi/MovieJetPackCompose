package com.bcr.moviejetpackcompose.ui.tv

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bcr.moviejetpackcompose.core.model.Movie
import com.bcr.moviejetpackcompose.core.viewmodels.TVViewModel
import com.bcr.moviejetpackcompose.ui.card.HMovieCard
import com.bcr.moviejetpackcompose.ui.theme.appTypography
import com.google.accompanist.pager.ExperimentalPagerApi

private lateinit var appNavController: NavHostController
private lateinit var viewmodel: TVViewModel


@ExperimentalPagerApi
@Composable
fun TVScreen(navController: NavHostController?, viewModel: TVViewModel) {
    viewmodel = viewModel
    navController?.let { appNavController = navController }
    Scaffold() {
        Column {
            AppBarTV()
            ContentTV()
        }
    }
}

@Composable
fun AppBarTV() {
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
                "TV",
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
fun ContentTV() {
    LazyColumn(modifier = Modifier
        .background(Color.White)
        .fillMaxSize()) {
        item { PopularMovieTV("Popular", viewmodel.popularMovies) }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        item { PopularMovieTV("On Air", viewmodel.onAirMovies) }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item { PopularMovieTV("Top Movie", viewmodel.topMovies) }
        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}


@Composable
fun PopularMovieTV(title: String, movies: List<Movie>) {
    Column {
        Text(text = title,
            modifier = Modifier.padding(16.dp),
            style = appTypography.body2.copy(fontWeight = FontWeight.Bold))

        LazyRow(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp)) {

            itemsIndexed(movies) { _, item ->
                HMovieCard(navController = appNavController, movie = item, false)
            }

        }

    }
}

