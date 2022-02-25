package com.bcr.moviejetpackcompose.viewmodel

import app.cash.turbine.test
import com.bcr.moviejetpackcompose.MainDispatcherRule
import com.bcr.moviejetpackcompose.core.viewmodels.HomeViewModel
import com.bcr.moviejetpackcompose.repositories.FakeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Rule

@ExperimentalCoroutinesApi
class TestHomeViewModel {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewmodel: HomeViewModel

    private lateinit var repository: FakeRepository

    @Before
    fun setup(){
        repository = FakeRepository()
        viewmodel = HomeViewModel(repository)
        viewmodel.getMovieList()
    }

    @Test
    fun `Test Get Upcoming Movies List`() = runTest {
        viewmodel.viewModelState.test {
            delay(300)
            assertEquals(viewmodel.viewModelState.value.upcomingMovies.count(), 1)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test Get Popular Movies List`() = runTest {
        viewmodel.viewModelState.test {
            delay(300)
            assertEquals(viewmodel.viewModelState.value.popularMovies.count(), 1)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test Get Top Rate Movies List`() = runTest {
        viewmodel.viewModelState.test {
            delay(300)
            assertEquals(viewmodel.viewModelState.value.topRateMovies.count(), 1)
            cancelAndIgnoreRemainingEvents()
        }
    }

}
