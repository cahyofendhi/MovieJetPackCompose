package com.bcr.moviejetpackcompose.ui

import androidx.compose.foundation.background
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bcr.moviejetpackcompose.core.viewmodels.*
import com.bcr.moviejetpackcompose.ui.app.*
import com.bcr.moviejetpackcompose.ui.home.HomeScreen
import com.bcr.moviejetpackcompose.ui.login.LoginScreen
import com.bcr.moviejetpackcompose.ui.theme.AppTheme
import com.bcr.moviejetpackcompose.ui.tv.TVScreen
import com.bcr.moviejetpackcompose.utils.EMAIL_FIELD_LOGIN_TAG
import com.bcr.moviejetpackcompose.utils.PASSWORD_FIELD_LOGIN_TAG
import com.bcr.moviejetpackcompose.utils.SUBMIT_BUTTON_LOGIN_TAG
import com.google.accompanist.pager.ExperimentalPagerApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @OptIn(ExperimentalPagerApi::class)
    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    private lateinit var navController: NavHostController

    @OptIn(ExperimentalPagerApi::class)
    private fun launchLoginScreenWithNavGraph() {
        composeTestRule.setContent {
            AppTheme {
                val appState = rememberMovieAppState()
                navController = appState.navController
                Scaffold(
                    scaffoldState = appState.scaffoldState,
                    modifier = Modifier.background(Color.White),
                    bottomBar = {
                        if (appState.shouldShowBottomBar) {
                            BottomNavigationBar(appState)
                        }
                    }
                ) {
                    NavigationGraphTest(appState)
                }
            }
        }
    }

    @ExperimentalPagerApi
    @Test
    fun test_login_in_valid_form() {
        launchLoginScreenWithNavGraph()
        with(composeTestRule) {
            val email = "testgmail.com"
            val password = "Password123"

            onNodeWithTag(EMAIL_FIELD_LOGIN_TAG)
                .performTextInput(email)

            onNodeWithTag(PASSWORD_FIELD_LOGIN_TAG)
                .performTextInput(password)

            onNodeWithTag(SUBMIT_BUTTON_LOGIN_TAG)
                .assertIsNotEnabled()
        }
    }

    @ExperimentalPagerApi
    @Test
    fun test_login_valid_form() {
        launchLoginScreenWithNavGraph()
        with(composeTestRule) {
            val email = "test@gmail.com"
            val password = "Password123"

            onNodeWithTag(EMAIL_FIELD_LOGIN_TAG)
                .performTextInput(email)

            onNodeWithTag(PASSWORD_FIELD_LOGIN_TAG)
                .performTextInput(password)

            onNodeWithTag(SUBMIT_BUTTON_LOGIN_TAG)
                .assertIsEnabled()

        }
    }

}


@ExperimentalPagerApi
@Composable
private fun NavigationGraphTest(appState: MovieAppState) {
    NavHost(appState.navController, startDestination = MainNavigation.Login.route) {

        composable(MainNavigation.Login.route) { _ ->
            LoginScreen(LoginViewModel(), onNext = {
                print("GO TO HOME SCREEN")
                appState.startNewPage(MainNavigation.Home.route)
            })
        }

        navigation(
            route = MainNavigation.Home.route,
            startDestination = HomeSections.Home.route
        ) {
            addHomeGraphTest(appState)
        }



    }
}

@ExperimentalPagerApi
private fun NavGraphBuilder.addHomeGraphTest(appState: MovieAppState) {
    val homeVM = HomeViewModel()
    val tvVM = TVViewModel()

    composable(HomeSections.Home.route) { _ ->
        HomeScreen(homeVM, onSearch = {}, onPressed = {})
    }

    composable(HomeSections.Tv.route) { _ ->
        TVScreen(tvVM, onSearch = {}, onPressed = {})
    }
}
