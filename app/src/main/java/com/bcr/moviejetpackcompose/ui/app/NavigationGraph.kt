package com.bcr.moviejetpackcompose.ui.app;

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bcr.moviejetpackcompose.core.model.GroupType
import com.bcr.moviejetpackcompose.core.viewmodels.*
import com.bcr.moviejetpackcompose.session
import com.bcr.moviejetpackcompose.ui.detail.DetailMovieScreen
import com.bcr.moviejetpackcompose.ui.detail.DetailTVScreen
import com.bcr.moviejetpackcompose.ui.home.HomeScreen
import com.bcr.moviejetpackcompose.ui.login.LoginScreen
import com.bcr.moviejetpackcompose.ui.onboard.OnBoardScreen
import com.bcr.moviejetpackcompose.ui.search.SearchScreen
import com.bcr.moviejetpackcompose.ui.tv.TVScreen
import com.google.accompanist.pager.ExperimentalPagerApi


@ExperimentalPagerApi
@Composable
fun NavigationGraph(appState: MovieAppState) {
    val route = if (!session.isOnboardDone) {
         MainNavigation.OnBoard.route
    } else if (!session.isLoginDone) {
        MainNavigation.Login.route
    } else {
        MainNavigation.Home.route
    }
    NavHost(appState.navController, startDestination = route) {
        navigation(
            route = MainNavigation.Home.route,
            startDestination = HomeSections.Home.route
        ) {
            addHomeGraph(appState)
        }

        composable(MainNavigation.OnBoard.route) {
            OnBoardScreen(onNext = {
                session.isOnboardDone = true
                appState.startNewPage(MainNavigation.Login.route)
            })
        }

        composable(MainNavigation.Login.route) { _ ->
            LoginScreen(LoginViewModel(), onNext = {
                session.isLoginDone = true
                appState.startNewPage(MainNavigation.Home.route)
            })
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
