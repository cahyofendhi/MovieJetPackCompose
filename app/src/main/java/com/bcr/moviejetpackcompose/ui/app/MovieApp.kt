package com.bcr.moviejetpackcompose.ui.app

import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bcr.moviejetpackcompose.core.model.GroupType
import com.bcr.moviejetpackcompose.core.viewmodels.DetailMovieViewModel
import com.bcr.moviejetpackcompose.core.viewmodels.DetailTVViewModel
import com.bcr.moviejetpackcompose.core.viewmodels.HomeViewModel
import com.bcr.moviejetpackcompose.core.viewmodels.TVViewModel
import com.bcr.moviejetpackcompose.ui.detail.DetailMovieScreen
import com.bcr.moviejetpackcompose.ui.detail.DetailTVScreen
import com.bcr.moviejetpackcompose.ui.home.HomeScreen
import com.bcr.moviejetpackcompose.ui.search.SearchScreen
import com.bcr.moviejetpackcompose.ui.theme.AppTheme
import com.bcr.moviejetpackcompose.ui.theme.blueYoung
import com.bcr.moviejetpackcompose.ui.tv.TVScreen
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


@ExperimentalPagerApi
@Composable
private fun NavigationGraph(appState: MovieAppState) {
    NavHost(appState.navController, startDestination = MainNavigation.Home.route) {
        navigation(
            route = MainNavigation.Home.route,
            startDestination = HomeSections.Home.route
        ) {
            addHomeGraph(appState)
        }
        composable(
            MainNavigation.MovieDetail.route,
            arguments = MainNavigation.MovieDetail.arguments) { backStackEntry ->
            val argument = requireNotNull(backStackEntry.arguments)
            MainNavigation.MovieDetail.getArguments(argument = argument)?.let {
                val viewmodel: DetailMovieViewModel = viewModel(
                    factory = DetailMovieViewModel.provideFactory(
                        movie = it,
                        owner = backStackEntry,
                        defaultArgs = backStackEntry.arguments
                    )
                )
                DetailMovieScreen(
                    viewModel = viewmodel,
                    onBack = appState::onBackPress,
                    onPressed = { data ->
                        appState.navigateToPage(MainNavigation.MovieDetail.createRouteWithArguments(data), backStackEntry)
                    }
                )
            }
        }

        composable(
            MainNavigation.TVDetail.route,
            arguments = MainNavigation.TVDetail.arguments) { backStackEntry ->
            val argument = requireNotNull(backStackEntry.arguments)
            MainNavigation.TVDetail.getArguments(argument)?.let {
                val viewmodel: DetailTVViewModel = viewModel(
                    factory = DetailTVViewModel.provideFactory(
                        movie = it,
                        owner = backStackEntry,
                        defaultArgs = backStackEntry.arguments
                    )
                )
                DetailTVScreen(
                    viewmodel = viewmodel,
                    onBack = appState::onBackPress,
                    onPressed = { data ->
                        appState.navigateToPage(MainNavigation.TVDetail.createRouteWithArguments(data), backStackEntry)
                    }
                )
            }
        }

        composable(
            MainNavigation.SearchPage.route,
            arguments = MainNavigation.SearchPage.arguments
        ) { backStackEntry ->
            val argument = requireNotNull(backStackEntry.arguments)
            MainNavigation.SearchPage.getArguments(argument)?.let { type ->
                SearchScreen(type = type, onBack = {
                    appState.onBackPress()
                }, onPressed = { dt ->
                    val isMovie = type == GroupType.movie.type
                    val route = if (isMovie) {
                        MainNavigation.MovieDetail.createRouteWithArguments(dt)
                    } else {
                        MainNavigation.TVDetail.createRouteWithArguments(dt)
                    }
                    appState.navigateToPage(route, backStackEntry)
                })
            }
        }
    }
}



@ExperimentalPagerApi
fun NavGraphBuilder.addHomeGraph(appState: MovieAppState) {
    val homeVM = HomeViewModel()
    val tvVM = TVViewModel()
    homeVM.getMovieList()
    tvVM.getMovieList()

    composable(HomeSections.Home.route) { backStack ->
        HomeScreen(homeVM, onSearch = {
            appState.navigateToPage(MainNavigation.SearchPage.createRouteWithArguments(GroupType.movie), backStack)
        }, onPressed = {
            appState.navigateToPage(MainNavigation.MovieDetail.createRouteWithArguments(it), backStack)
        })
    }

    composable(HomeSections.Tv.route) { backStack ->
        TVScreen(tvVM, onSearch = {
            appState.navigateToPage(MainNavigation.SearchPage.createRouteWithArguments(GroupType.tv), backStack)
        }, onPressed = {
            appState.navigateToPage(MainNavigation.TVDetail.createRouteWithArguments(it), backStack)
        })
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