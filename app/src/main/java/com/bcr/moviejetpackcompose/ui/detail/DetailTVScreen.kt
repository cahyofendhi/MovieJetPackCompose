package com.bcr.moviejetpackcompose.ui.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.bcr.moviejetpackcompose.core.model.Movie
import com.bcr.moviejetpackcompose.core.viewmodels.DetailTVViewModel
import com.bcr.moviejetpackcompose.ui.card.CreditPeopleCard
import com.bcr.moviejetpackcompose.ui.card.LabelRating
import com.bcr.moviejetpackcompose.ui.card.SimiliarMovieCard
import com.bcr.moviejetpackcompose.ui.components.TagLabel
import com.bcr.moviejetpackcompose.ui.theme.appTypography
import com.bcr.moviejetpackcompose.ui.theme.primaryBlack
import com.bcr.moviejetpackcompose.ui.theme.white
import com.bcr.moviejetpackcompose.utils.extensions.configureProgress
import com.bcr.moviejetpackcompose.utils.extensions.price
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.insets.ui.TopAppBar
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

private lateinit var appNavController: NavHostController
private lateinit var viewmodel: DetailTVViewModel

@Composable
fun DetailTVScreen(navController: NavHostController,
                   movie: Movie) {
    viewmodel = DetailTVViewModel()
    viewmodel.getDetailMovie(movie)
    appNavController = navController
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        val collapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
        CollapsingToolbarScaffold(modifier = Modifier.fillMaxSize(),
            state = collapsingToolbarScaffoldState,
            scrollStrategy = ScrollStrategy.EnterAlways,
            toolbar = {
                val progress = collapsingToolbarScaffoldState.toolbarState.progress
                Box(modifier = Modifier
                    .background(color = primaryBlack)
                    .parallax(0.5f)
                    .graphicsLayer {
                        alpha = progress
                    }
                ) {
                    BoxWithConstraints(modifier = Modifier.background(color = Color.White)) {
                        Image(
                            painter = rememberImagePainter(data = viewmodel.movie?.getImageBackdrop()),
                            contentDescription = "Forest Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .height(360.dp)
                                .clip(RoundedCornerShape(bottomStart = 32.dp))
                        )
                        Column() {
                            Spacer(modifier = Modifier.height(300.dp))
                            Box(modifier = Modifier.padding(start = 32.dp, top = 16.dp, bottom = 16.dp)) {
                                Card(backgroundColor = Color.White,
                                    shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp),
                                    elevation = 5.dp,) {
                                    Row(modifier = Modifier
                                        .padding(16.dp),
                                    ) {
                                        Column(modifier = Modifier.weight(1f),
                                            horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(text = "${viewmodel.movie?.numberOfEpisodes}", style = appTypography.subtitle2)
                                            Text(text = "Episode", style = appTypography.body2, modifier = Modifier.padding(top = 8.dp))
                                        }
                                        Column(modifier = Modifier.weight(1f),
                                            horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(text = "${viewmodel.movie?.popularity}", style = appTypography.subtitle2)
                                            Text(text = "Popularity", style = appTypography.body2, modifier = Modifier.padding(top = 8.dp))
                                        }
                                        Column(modifier = Modifier.weight(1f),
                                            horizontalAlignment = Alignment.CenterHorizontally) {
                                            LabelRating(rate = viewmodel.movie?.voteAverage!!)
                                            Text(text = "Popularity", style = appTypography.body2, modifier = Modifier.padding(top = 8.dp))
                                        }
                                    }
                                }
                            }   
                        }
                    }
                }
                TopAppBar(
                    backgroundColor = Color.Transparent,
                    navigationIcon = { BackButton(navController = appNavController) },
                    title = {
                        val progressReversed = 1f - progress
                        Text(text = "Detail Movie",
                            style = appTypography.body1,
                            color = Color.White,
                            modifier = Modifier.alpha(progressReversed.configureProgress(0.5f))
                        )
                    },
                    elevation = 0.dp
                )
            },
            body = { TVContent() })

    }
}

@Composable
private fun TVContent() {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .background(white)) {
        item {
            viewmodel.movie?.let {
                Column(modifier = Modifier.padding(start = 8.dp, top = 16.dp, end = 16.dp)) {
                    Text(
                        text = it.getTitleMovie(),
                        style = appTypography.subtitle2.copy(lineHeight = 50.sp),
                        modifier = Modifier.padding(start = 8.dp, end = 16.dp)
                    )
                    TagLabel(tags = it.allGenre())
                    Text(
                        text = it.overview!!,
                        style = appTypography.body2,
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
                    )
                }
            }

        }

        item {
            LazyRow(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
                contentPadding = PaddingValues(16.dp, end = 16.dp)
            ) {
                itemsIndexed(viewmodel.casts) { index, item ->
                    CreditPeopleCard(item)
                }
            }
        }

        item {
            viewmodel.movie?.let {
                Column(modifier = Modifier.padding(
                    start =  16.dp,
                    end = 16.dp,
                    top = 32.dp,
                    bottom = 16.dp)
                ) {
                    InfoMovieItem(title = "Status", value = ": ${it.status}")
                    InfoMovieItem(title = "Type", value = ": ${it.type}")
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
                SimiliarMovieTV()
            }
        }

    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SimiliarMovieTV() {
    val width = LocalConfiguration.current.screenWidthDp - 16
    FlowRow() {
        repeat(viewmodel.similiarMovies.count()) {
            Box(modifier = Modifier
                .width((width / 4).dp)
                .wrapContentHeight()) {
                SimiliarMovieCard(width, appNavController, viewmodel.similiarMovies[it],false)
            }
        }
    }
}
