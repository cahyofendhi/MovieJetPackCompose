package com.bcr.moviejetpackcompose.ui.app

import android.os.Bundle
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.bcr.moviejetpackcompose.R
import com.bcr.moviejetpackcompose.core.model.GroupType
import com.bcr.moviejetpackcompose.core.model.Movie
import com.bcr.moviejetpackcompose.utils.extensions.fromJson
import com.bcr.moviejetpackcompose.utils.extensions.toJson

enum class HomeSections(
    val title: String,
    val icon: Int,
    val route: String
) {
    Home("Home", R.drawable.ic_home, "home/home"),
    Tv("TV", R.drawable.ic_tv, "home/search"),
}

sealed class MainNavigation(var route: String) {
    object Home: MainNavigation("main")
    object MovieDetail : MainNavigation(
        "${Keys.routeName}?${Keys.movie}={${Keys.movie}}") {
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

    object TVDetail : MainNavigation(
        "${Keys.routeName}?${Keys.movie}={${Keys.movie}}") {
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

    object SearchPage : MainNavigation(
        "${Keys.routeName}?${Keys.type}={${Keys.type}}") {
        object Keys {
            const val routeName = "search-page"
            const val type = "category-type"
        }
        val arguments = listOf(
            navArgument(Keys.type) { type = NavType.StringType }
        )

        fun createRouteWithArguments(type: GroupType): String {
            return Keys.routeName.plus("?${Keys.type}=${type.type}")
        }

        fun getArguments(argument :Bundle): String? {
            return argument.getString(Keys.type)
        }

    }
}
