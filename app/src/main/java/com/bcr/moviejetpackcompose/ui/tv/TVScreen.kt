package com.bcr.moviejetpackcompose.ui.tv

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bcr.moviejetpackcompose.core.model.Movie
import com.bcr.moviejetpackcompose.core.viewmodels.TVViewModel
import com.bcr.moviejetpackcompose.core.viewmodels.TVViewModelState
import com.bcr.moviejetpackcompose.ui.card.HMovieCard
import com.bcr.moviejetpackcompose.ui.components.HShimmerAnimation
import com.bcr.moviejetpackcompose.ui.components.SearchButton
import com.bcr.moviejetpackcompose.ui.theme.appTypography
import com.bcr.moviejetpackcompose.ui.theme.white
import com.bcr.moviejetpackcompose.utils.extensions.isScrolled
import com.google.accompanist.pager.ExperimentalPagerApi

private lateinit var uiState: State<TVViewModelState>

@ExperimentalPagerApi
@Composable
fun TVScreen(viewModel: TVViewModel,
             onSearch: () -> Unit,
             onPressed: (Movie) -> Unit) {
    uiState = viewModel.uiState.collectAsState()
    val lazyListState = rememberLazyListState()
    Scaffold(
        topBar = { AppBarTV(lazyListState = lazyListState, onSearch) }
    ) {
        ContentTV(lazyListState, onPressed)
    }
}

@Composable
fun AppBarTV(lazyListState: LazyListState, onSearch: () -> Unit) {
    TopAppBar(
        title = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "TV",
                    style = appTypography.h6.copy(textAlign = TextAlign.Center),
                )
            }
        },
        navigationIcon = {},
        actions = { SearchButton(onClick =  onSearch ) },
        backgroundColor = white,
        elevation = if (!lazyListState.isScrolled) 0.dp else 3.dp
    )
}

@ExperimentalPagerApi
@Composable
fun ContentTV(lazyListState: LazyListState,
              onPressed: (Movie) -> Unit) {
    LazyColumn(state = lazyListState,
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()) {
        item {
            if (uiState.value.isLoadPopular) {
                HShimmerAnimation()
            } else {
                PopularMovieTV("Popular", uiState.value.popularMovies, onPressed)
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            if (uiState.value.isLoadOnAir) {
                HShimmerAnimation()
            } else {
                PopularMovieTV("On Air", uiState.value.onAirMovies, onPressed)
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            if (uiState.value.isLoadTopRate) {
                HShimmerAnimation()
            } else {
                PopularMovieTV("Top Movie", uiState.value.topMovies, onPressed)
            }
        }
        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun PopularMovieTV(title: String,
                   movies: List<Movie>,
                   onPressed: (Movie) -> Unit) {
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
                HMovieCard(item, onPressed = { onPressed(item) })
            }

        }

    }
}

