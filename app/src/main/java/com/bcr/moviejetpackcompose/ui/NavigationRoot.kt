package com.bcr.moviejetpackcompose.ui

import android.os.Bundle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.bcr.moviejetpackcompose.R
import com.bcr.moviejetpackcompose.core.model.Movie
import com.bcr.moviejetpackcompose.utils.extensions.fromJson
import com.bcr.moviejetpackcompose.utils.extensions.toJson

sealed class NavigationRoot(var route: String, var icon: Int?, var title: String) {
    object Home: NavigationRoot("home", R.drawable.ic_home, "Home")
    object Search: NavigationRoot("search", R.drawable.ic_search, "Search")
    object MovieDetail : NavigationRoot(
        "${Keys.routeName}?${Keys.movie}={${Keys.movie}}", null, "Movie"
    ) {
        object Keys {
            const val routeName = "movie-detail"
            const val movie = "movie-data"
        }
        val arguments = listOf(
            navArgument(Keys.movie) { type = NavType.StringType }
        )
        fun createRouteWithArguments(movie: Movie): String {
            val data = movie.toJson()
            return Keys.routeName.plus("?${Keys.movie}=$data")
        }

        fun getArguments(argument :Bundle): Movie? {
            val json = argument.getString(Keys.movie)
            json?.let {
                return it.fromJson(Movie::class.java)
            }
            return null
        }

    }

    object TVDetail : NavigationRoot(
        "${Keys.routeName}?${ Keys.movie }={${Keys.movie}}", null, "TV Movie"
    ) {
        object Keys {
            const val routeName = "tv-detail"
            const val movie = "tv-movie"
        }
        val arguments = listOf(
            navArgument(Keys.movie) { type = NavType.StringType }
        )

        fun createRouteWithArguments(movie: Movie): String {
            val data = movie.toJson()
            return Keys.routeName.plus("?${Keys.movie}=$data")
        }

        fun getArguments(argument :Bundle): Movie? {
            val json = argument.getString(Keys.movie)
            json?.let {
                return it.fromJson(Movie::class.java)
            }
            return null
        }

    }
}
