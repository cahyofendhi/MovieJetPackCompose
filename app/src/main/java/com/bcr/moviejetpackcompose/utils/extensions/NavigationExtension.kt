package com.bcr.moviejetpackcompose.utils.extensions

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.core.net.toUri
import androidx.navigation.*
import androidx.navigation.compose.currentBackStackEntryAsState

fun NavController.navigatePage(
    route: String,
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val routeLink = NavDeepLinkRequest
        .Builder
        .fromUri(NavDestination.createRoute(route).toUri())
        .build()

    val deepLinkMatch = graph.matchDeepLink(routeLink)
    if (deepLinkMatch != null) {
        val destination = deepLinkMatch.destination
        val id = destination.id
        navigate(id, args, navOptions, navigatorExtras)
    } else {
        navigate(route, navOptions, navigatorExtras)
    }
}

@Composable
fun NavController.currentRoute(): String {
    val navBackStackEntry = currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination
    return if (currentRoute?.route != null) {
        currentRoute.route!!
    } else {
        ""
    }
}