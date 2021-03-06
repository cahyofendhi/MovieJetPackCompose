package com.bcr.moviejetpackcompose.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.bcr.moviejetpackcompose.core.model.Movie
import com.bcr.moviejetpackcompose.core.viewmodels.HomeViewModel
import com.bcr.moviejetpackcompose.core.viewmodels.HomeViewModelState
import com.bcr.moviejetpackcompose.ui.card.HMovieCard
import com.bcr.moviejetpackcompose.ui.card.VMovieCard
import com.bcr.moviejetpackcompose.ui.components.AppImage
import com.bcr.moviejetpackcompose.ui.components.HShimmerAnimation
import com.bcr.moviejetpackcompose.ui.components.SearchButton
import com.bcr.moviejetpackcompose.ui.components.ShimmerAnimation
import com.bcr.moviejetpackcompose.ui.theme.appTypography
import com.bcr.moviejetpackcompose.ui.theme.white
import com.bcr.moviejetpackcompose.utils.extensions.isScrolled
import com.google.accompanist.pager.ExperimentalPagerApi

private lateinit var uiState: State<HomeViewModelState>

@ExperimentalPagerApi
@Composable
fun HomeScreen(viewModel: HomeViewModel,
               onSearch: () -> Unit,
               onPressed: (Movie) -> Unit) {
    uiState = viewModel.uiState.collectAsState()
    val lazyListState = rememberLazyListState()
    Scaffold(
        topBar = { AppBarHome(lazyListState, onSearch) }
    ) {
        ContentHome(lazyListState, onPressed)
    }

}

@Composable
fun AppBarHome(lazyListState: LazyListState, onSearch: () -> Unit) {
    TopAppBar(
        title = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Movie",
                    style = appTypography.h6.copy(textAlign = TextAlign.Center),
                )
            }
        },
        navigationIcon = {},
        actions = { SearchButton(onClick = { onSearch() }) },
        backgroundColor = white,
        elevation = if (!lazyListState.isScrolled) 0.dp else 3.dp
    )
}

@ExperimentalPagerApi
@Composable
fun ContentHome(state: LazyListState, onPressed: (Movie) -> Unit) {
    LazyColumn(state = state,
        modifier = Modifier
        .background(Color.White)
        .fillMaxSize()) {
        item { UpcomingHome(onPressed) }
        item { PopularMovieHome(onPressed) }

        item {
            Spacer(modifier = Modifier.padding(16.dp))
        }

        item {
            Text(text = "Top Movie",
                modifier = Modifier.padding(16.dp),
                style = appTypography.body2.copy(fontWeight = FontWeight.Bold))
        }

        if (uiState.value.isLoadTopRate) {
            repeat(5) {
                item { ShimmerAnimation(false) }
            }
        } else {
            itemsIndexed(uiState.value.upcomingMovies) { index, item ->
                VMovieCard(item, onPressed = { onPressed(item) })
            }
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@ExperimentalPagerApi
@Composable
fun UpcomingHome(onPressed: (Movie) -> Unit) {
    val width = LocalConfiguration.current.screenWidthDp / 2.5

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height((width / 1.5).dp)
            .padding(vertical = 14.dp),
        contentPadding = PaddingValues(horizontal = 14.dp)
    ) {
        if (uiState.value.isLoadUpcoming) {
            repeat(10) {
                item {
                    ShimmerAnimation(true, (width / 1.5))
                }
            }
        } else {
            itemsIndexed(uiState.value.upcomingMovies) { index, item ->
                AppImage(
                    url = item.getImageBackdrop(),
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .fillMaxHeight()
                        .width(width.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { onPressed(item) }
                )
            }
        }
    }
}

@Composable
fun PopularMovieHome(onPressed: (Movie) -> Unit) {
    Column {
        Text(text = "Popular Movie",
            modifier = Modifier.padding(16.dp),
            style = appTypography.body2.copy(fontWeight = FontWeight.Bold))

        if (uiState.value.isLoadPopular) {
            HShimmerAnimation()
        } else {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
            ) {

                itemsIndexed(uiState.value.popularMovies) { _, item ->
                    HMovieCard(movie = item, onPressed = { onPressed(item) })
                }

            }
        }
    }
}

@Preview(showBackground = true)
@ExperimentalPagerApi
@Composable
fun HomeScreenPreview() {
    HomeScreen(HomeViewModel(), onSearch = {}, onPressed = {})
}