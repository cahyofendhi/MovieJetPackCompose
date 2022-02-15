package com.bcr.moviejetpackcompose.ui.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bcr.moviejetpackcompose.ui.theme.white
import com.bcr.moviejetpackcompose.ui.card.CreditPeopleCard
import com.bcr.moviejetpackcompose.ui.card.LabelRating
import com.bcr.moviejetpackcompose.ui.card.SimiliarMovieCard
import com.bcr.moviejetpackcompose.ui.components.TagLabel
import com.bcr.moviejetpackcompose.ui.theme.appTypography
import com.google.accompanist.flowlayout.FlowRow

private lateinit var appNavController: NavHostController

@Composable
fun DetailMovieScreen(navController: NavHostController,
                      movieId: Int) {
    appNavController = navController
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
    ) {
        AppScaffold( modifier = Modifier.fillMaxSize() )
    }
}

@Composable
private fun AppScaffold(
    modifier: Modifier = Modifier,
) {
    AppBarScaffold(
        navController = appNavController,
        modifier = modifier.fillMaxSize(),
    ) {
        BodyContentMovie()
    }
}

@Composable
fun BodyContentMovie() {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .background(white)) {
        item {
            Column(modifier = Modifier.padding(start = 8.dp, top = 16.dp, end = 16.dp)) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "The Power Of Spiderman",
                        style = appTypography.subtitle2.copy(lineHeight = 50.sp) ,
                        modifier = Modifier.padding(end = 16.dp))
                    LabelRating(rate = 8.3)
                }
                TagLabel(tags = listOf("Action", "Comedy", "Crime"))
                Text(
                    text = "On a school field trip, Peter Parker (Maguire) is bitten by a genetically modified spider. He wakes up the next morning with incredible powers. After witnessing the death of his uncle (Robertson), Parkers decides to put his new skills to use in order to rid the city of evil, but someone else has other plans. The Green Goblin (Dafoe) sees Spider-Man as a threat and must dispose of him. Even if it means the Goblin has to target Parker's Aunt (Harris) and the girl he secretly pines for (Dunst)\n" +
                            "\n",
                    style = appTypography.body2,
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
                )
            }

        }

        item {
            LazyRow(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
                contentPadding = PaddingValues(16.dp, end = 16.dp)
            ) {
                items(10) {
                    CreditPeopleCard()
                }
            }
        }

        item {
            Column(modifier = Modifier.padding(
                start =  16.dp,
                end = 16.dp,
                top = 32.dp,
                bottom = 16.dp)
            ) {
                InfoMovieItem(title = "Status", value = ": Runtime")
                InfoMovieItem(title = "RunTime", value = ": 120")
                InfoMovieItem(title = "Premiere", value = ": 20/12/2022")
                InfoMovieItem(title = "Budget", value = ": $1.000")
                InfoMovieItem(title = "Revenue", value = ": $1.000")
            }
        }

        item {
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(text = "Similiar Movie",
                    style = appTypography.subtitle2,
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp))
                SimiliarMovie()
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
fun SimiliarMovie() {
    val width = LocalConfiguration.current.screenWidthDp - 16
    val data = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    FlowRow() {
        repeat(data.count()) {
            Box(modifier = Modifier
                .width((width / 4).dp)
                .wrapContentHeight()) {
                SimiliarMovieCard(width, appNavController)
            }
        }
    }
}