package com.bcr.moviejetpackcompose.ui

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.bcr.moviejetpackcompose.R

sealed class NavigationRoot(var route: String, var icon: Int?, var title: String) {
    object Home: NavigationRoot("home", R.drawable.ic_home, "Home")
    object Search: NavigationRoot("search", R.drawable.ic_search, "Search")
    object MovieDetail : NavigationRoot(
        Keys.routeName.plus("/{${Keys.movieId}}"), null, "Movie"
    ) {
        object Keys {
            const val routeName = "movie-detail"
            const val movieId = "movieId"
        }
        val arguments = listOf(
            navArgument(Keys.movieId) { type = NavType.IntType }
        )
        fun createRouteWithArguments(movieId: Int) =
            Keys.routeName.plus("/$movieId")
    }
    object TVDetail : NavigationRoot(
        Keys.routeName.plus("/{${Keys.movieId}}"), null, "TV Movie"
    ) {
        object Keys {
            const val routeName = "tv-detail"
            const val movieId = "movieId"
        }
        val arguments = listOf(
            navArgument(Keys.movieId) { type = NavType.IntType }
        )
        fun createRouteWithArguments(movieId: Int) =
            Keys.routeName.plus("/$movieId")
    }
}
