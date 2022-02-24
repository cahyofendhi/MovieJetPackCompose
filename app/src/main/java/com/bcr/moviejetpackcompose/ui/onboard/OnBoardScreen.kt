package com.bcr.moviejetpackcompose.ui.onboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.bcr.moviejetpackcompose.core.model.OnBoard

import com.bcr.moviejetpackcompose.R
import androidx.compose.ui.unit.dp
import com.bcr.moviejetpackcompose.ui.components.AppScaffold
import com.bcr.moviejetpackcompose.ui.theme.appTypography
import com.bcr.moviejetpackcompose.ui.theme.gray
import com.bcr.moviejetpackcompose.ui.theme.primaryBlack
import com.bcr.moviejetpackcompose.ui.theme.white
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

val onBoardPages = listOf(
    OnBoard("Onboard Page 1", "Description OnBoard Page 1", R.drawable.onboard),
    OnBoard("Onboard Page 2", "Description OnBoard Page 2", R.drawable.onboard),
    OnBoard("Onboard Page 3", "Description OnBoard Page 3", R.drawable.onboard),
)

@ExperimentalPagerApi
@Composable
fun OnBoardScreen(onNext: () -> Unit) {

    val pagerState = rememberPagerState(initialPage = 0)

    AppScaffold {

        Column(modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
            ) {

            HorizontalPager(
                count = onBoardPages.count(),
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) { page ->

                ContentOnBoard(onBoard = onBoardPages[page])

            }

            Row(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .weight(0.15f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Spacer(modifier = Modifier.weight(0.33f))
                
                HorizontalPagerIndicator(pagerState = pagerState,
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(0.33f),
                    activeColor = primaryBlack,
                    inactiveColor = gray,
                )

                Box(modifier = Modifier.weight(0.33f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    this@Row.AnimatedVisibility(visible = pagerState.currentPage == 2) {
                        Button( modifier = Modifier.wrapContentSize(),
                            onClick = onNext) {
                            Text(text = "Next", style = appTypography.subtitle2.copy(white))
                        }
                    }
                }

            }

        }

    }
}


@Composable
fun ContentOnBoard(onBoard: OnBoard) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Box(modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Image(painter = painterResource(id = onBoard.image),
                contentDescription = null,
                modifier = Modifier
                    .size(250.dp)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(text = onBoard.title, style = appTypography.subtitle1)

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = onBoard.description, style = appTypography.caption)
        }

    }
}
