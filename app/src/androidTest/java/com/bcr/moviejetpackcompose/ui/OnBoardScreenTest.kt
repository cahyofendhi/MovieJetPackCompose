package com.bcr.moviejetpackcompose.ui

import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.bcr.moviejetpackcompose.ui.onboard.OnBoardScreen
import com.bcr.moviejetpackcompose.ui.theme.AppTheme
import com.bcr.moviejetpackcompose.utils.NEXT_ONBOARD_TAG
import com.bcr.moviejetpackcompose.utils.PAGER_ONBOARD_TAG
import com.google.accompanist.pager.ExperimentalPagerApi
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class OnBoardScreenTest {

    @OptIn(ExperimentalPagerApi::class)
    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @ExperimentalPagerApi
    private fun launchOnboardScreen() {
        composeTestRule.setContent {
            AppTheme {
                OnBoardScreen {}
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Test
    fun test_select_button() {
        launchOnboardScreen()
        with(composeTestRule) {
            onNodeWithTag(PAGER_ONBOARD_TAG).performTouchInput {
                swipeLeft()
                swipeLeft()
            }
            onNodeWithTag(NEXT_ONBOARD_TAG)
                .assertIsDisplayed()

            onNodeWithTag(NEXT_ONBOARD_TAG)
                .performClick()
        }
    }

}

