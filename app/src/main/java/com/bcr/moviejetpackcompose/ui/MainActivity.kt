package com.bcr.moviejetpackcompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bcr.moviejetpackcompose.ui.app.MovieApp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

@ExperimentalPagerApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupAppCenter()
        setContent {
            MovieApp()
        }
    }

    private fun setupAppCenter() {
        AppCenter.start(
            application, "891a39dc-042f-44bd-a20f-9feb3a9f2730",
            Analytics::class.java, Crashes::class.java
        )
    }
}
