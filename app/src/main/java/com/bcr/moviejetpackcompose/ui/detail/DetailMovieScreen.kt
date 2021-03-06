package com.bcr.moviejetpackcompose.ui.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bcr.moviejetpackcompose.core.model.Movie
import com.bcr.moviejetpackcompose.core.viewmodels.DetailMovieViewModel
import com.bcr.moviejetpackcompose.core.viewmodels.DetailViewModelState
import com.bcr.moviejetpackcompose.ui.theme.white
import com.bcr.moviejetpackcompose.ui.card.CreditPeopleCard
import com.bcr.moviejetpackcompose.ui.card.LabelRating
import com.bcr.moviejetpackcompose.ui.card.SimiliarMovieCard
import com.bcr.moviejetpackcompose.ui.components.PeopleShimmerItem
import com.bcr.moviejetpackcompose.ui.components.SimiliarShimmerItem
import com.bcr.moviejetpackcompose.ui.components.TagLabel
import com.bcr.moviejetpackcompose.ui.theme.appTypography
import com.bcr.moviejetpackcompose.utils.extensions.price
import com.google.accompanist.flowlayout.FlowRow

private lateinit var uiState: State<DetailViewModelState>

@Composable
fun DetailMovieScreen(viewModel: DetailMovieViewModel,
                      onBack: () -> Unit,
                      onPressed: (Movie) -> Unit) {
    uiState = viewModel.uiState.collectAsState()
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
    ) {
        AppScaffold(
            modifier = Modifier.fillMaxSize(),
            onBack,
            onPressed)
    }
}

@Composable
private fun AppScaffold(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onPressed: (Movie) -> Unit
) {
    uiState.value.movie?.getImageBackdrop()?.let {
        AppBarScaffold(it,
            title = uiState.value.movie?.getTitleMovie() ?: "Detail Movie",
            onBack = onBack,
            modifier = modifier.fillMaxSize(),
    ) {
        BodyContentMovie(onPressed = onPressed)
      }
    }
}

@Composable
fun BodyContentMovie(onPressed: (Movie) -> Unit) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .background(white)) {

        item {
            uiState.value.movie?.let { movie ->
                Column(modifier = Modifier.padding(start = 8.dp, top = 16.dp, end = 16.dp)) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = movie.getTitleMovie(),
                            style = appTypography.subtitle1.copy(lineHeight = 50.sp) ,
                            modifier = Modifier.padding(end = 16.dp))
                        movie.voteAverage?.let { LabelRating(rate = it) }
                    }
                    TagLabel(tags = movie.allGenre())
                    movie.overview?.let {
                        Text(
                            text = it,
                            style = appTypography.body2,
                            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
                        )
                    }
                }
            }
        }

        item {
            LazyRow(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
                contentPadding = PaddingValues(16.dp, end = 16.dp)
            ) {
                if (uiState.value.isLoadCredit) {
                    repeat(10) {
                     item { PeopleShimmerItem() }
                    }
                } else {
                    itemsIndexed(uiState.value.casts) { index, item ->
                        CreditPeopleCard(item)
                    }
                }
            }
        }

        item {
            uiState.value.movie?.let { it
                Column(modifier = Modifier.padding(
                    start =  16.dp,
                    end = 16.dp,
                    top = 32.dp,
                    bottom = 16.dp)
                ) {
                    InfoMovieItem(title = "Status", value = ": ${it.status}")
                    InfoMovieItem(title = "RunTime", value = ": ${it.revenue}")
                    InfoMovieItem(title = "Premiere", value = ": ${it.getDateMovie()}")
                    InfoMovieItem(title = "Budget", value = ": ${it.budget.price()}")
                    InfoMovieItem(title = "Revenue", value = ": ${it.revenue.price()}")
                }
            }
        }

        item {
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(text = "Similiar Movie",
                    style = appTypography.subtitle2,
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp))
                SimiliarMovie(onPressed = onPressed)
            }
        }

    }
}

@Composable
fun InfoMovieItem(title: String, value: String) {
    Row(modifier = Modifier.padding(bottom = 8.dp)) {
        Text(text = title, style = appTypography.subtitle2, modifier = Modifier.weight(1F))
        Text(text = value, style = appTypography.body2, modifier = Modifier.weight(3F))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SimiliarMovie(onPressed: (Movie) -> Unit) {
    val width = LocalConfiguration.current.screenWidthDp - 16
    FlowRow() {
        if (uiState.value.isLoadSimiliar) {
            repeat(10) {
                SimiliarShimmerItem()
            }
        } else {
            repeat(uiState.value.similiarMovies.count()) {
                Box(
                    modifier = Modifier
                        .width((width / 4).dp)
                        .wrapContentHeight()
                ) {
                    SimiliarMovieCard(width, uiState.value.similiarMovies[it], onPressed = {
                        onPressed(uiState.value.similiarMovies[it])
                    })
                }
            }
        }
    }
}