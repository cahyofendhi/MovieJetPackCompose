package com.bcr.moviejetpackcompose.ui.app

import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.bcr.moviejetpackcompose.ui.theme.AppTheme
import com.bcr.moviejetpackcompose.ui.theme.blueYoung
import com.bcr.moviejetpackcompose.utils.extensions.currentRoute
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi


@ExperimentalPagerApi
@Composable
fun MovieApp() {
    ProvideWindowInsets {
        AppTheme {
            val appState = rememberMovieAppState()
            Scaffold(
                scaffoldState = appState.scaffoldState,
                modifier = Modifier.background(Color.White),
                bottomBar = {
                    if (appState.shouldShowBottomBar) {
                        BottomNavigationBar(appState)
                    }
                }
            ) {
                NavigationGraph(appState)
            }
        }
    }
}

@Composable
private fun BottomNavigationBar(appState: MovieAppState) {
    val items = listOf(
        HomeSections.Home,
        HomeSections.Tv
    )
    val routeName = appState.navController.currentRoute()
    if (routeName == HomeSections.Home.route ||
        routeName == HomeSections.Tv.route) {
        BottomNavigation(
            backgroundColor = blueYoung,
            contentColor = Color.White
        ) {
            items.forEach { item ->
                BottomNavigationItem(
                    selected = routeName == item.route,
                    onClick = { appState.navigateToBottomBarRoute(item.route) },
                    icon = {
                        Icon(
                            painterResource(id = item.icon),
                            contentDescription = item.title
                        )
                    },
                    label = {
                        Text(text = item.title, style = MaterialTheme.typography.body2)
                    },
                    alwaysShowLabel = true,
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.Black.copy(0.4f),
                )
            }
        }
    }
}