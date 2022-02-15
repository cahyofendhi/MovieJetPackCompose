package com.bcr.moviejetpackcompose.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.bcr.moviejetpackcompose.R
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.bcr.moviejetpackcompose.ui.detail.DetailMovieScreen
import com.bcr.moviejetpackcompose.ui.detail.DetailTVScreen
import com.bcr.moviejetpackcompose.ui.home.HomeScreen
import com.bcr.moviejetpackcompose.ui.theme.AppTheme
import com.bcr.moviejetpackcompose.ui.theme.blueYoung
import com.bcr.moviejetpackcompose.ui.tv.TVScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                window?.statusBarColor = 0xFF4A536D.toInt()
                MainScreen()
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.background(Color.White),
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        NavigationGraph(navController)
    }
}

@Composable
fun currentRoute(navController: NavController): String {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination
    return if (currentRoute?.route != null) {
        currentRoute.route!!
    } else {
        ""
    }
}

@ExperimentalPagerApi
@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationRoot.Home.route) {
        composable(NavigationRoot.Home.route) {
            HomeScreen(navController)
        }
        composable(NavigationRoot.Search.route) {
            TVScreen(navController)
        }
        composable(NavigationRoot.MovieDetail.route,
            arguments = NavigationRoot.MovieDetail.arguments) { backStackEntry ->
            val argument = requireNotNull(backStackEntry.arguments)
            DetailMovieScreen(navController = navController, movieId = argument.getInt(NavigationRoot.MovieDetail.Keys.movieId))
        }
        composable(NavigationRoot.TVDetail.route,
            arguments = NavigationRoot.TVDetail.arguments) { backStackEntry ->
            val argument = requireNotNull(backStackEntry.arguments)
            DetailTVScreen(navController = navController, movieId = argument.getInt(NavigationRoot.TVDetail.Keys.movieId))
        }
    }
}

@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}

@Composable
fun TopBar() {
   TopAppBar(
       title = { Text(text = stringResource(id = R.string.app_name), style = MaterialTheme.typography.h5) },
       backgroundColor = colorResource(id = R.color.blue_young),
       contentColor = Color.White,
   ) 
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar()
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationRoot.Home,
        NavigationRoot.Search
    )
    val routeName = currentRoute(navController = navController)
    if (routeName == NavigationRoot.Home.route || routeName == NavigationRoot.Search.route) {
        BottomNavigation(
            backgroundColor = blueYoung,
            contentColor = Color.White
        ) {
            items.forEach { item ->
                BottomNavigationItem(
                    selected = routeName == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painterResource(id = item.icon!!),
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