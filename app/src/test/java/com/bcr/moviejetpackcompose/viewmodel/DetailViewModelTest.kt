package com.bcr.moviejetpackcompose.viewmodel

import app.cash.turbine.test
import com.bcr.moviejetpackcompose.MainDispatcherRule
import com.bcr.moviejetpackcompose.core.viewmodels.DetailMovieViewModel
import com.bcr.moviejetpackcompose.repositories.FakeRepository
import com.bcr.moviejetpackcompose.resource.FakeResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewmodel: DetailMovieViewModel

    private lateinit var repository: FakeRepository

    @Before
    fun setup(){
        repository = FakeRepository()
        val movie = FakeResponse.dataMovies().results?.get(0)
        viewmodel = DetailMovieViewModel(movie, repository)
    }

    @Test
    fun `Test Get Movie`() = runTest {
        viewmodel.uiState.test {
            delay(300)
            Assert.assertEquals(viewmodel.uiState.value.movie?.id, 634649)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test Get Crew`() = runTest {
        viewmodel.uiState.test {
            delay(300)
            Assert.assertEquals(viewmodel.uiState.value.casts[0].id, 1136406)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test Get Similiar Movie`() = runTest {
        viewmodel.uiState.test {
            delay(1000)
            Assert.assertEquals(viewmodel.uiState.value.similiarMovies[0].id, 100402)
            cancelAndIgnoreRemainingEvents()
        }
    }


}
