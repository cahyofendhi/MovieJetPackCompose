package com.bcr.moviejetpackcompose.core.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcr.moviejetpackcompose.core.model.CategoryType
import com.bcr.moviejetpackcompose.core.model.GroupType
import com.bcr.moviejetpackcompose.core.model.Movie
import com.bcr.moviejetpackcompose.core.network.ResultWrapper
import com.bcr.moviejetpackcompose.core.repositories.MovieRepositoryImpl
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

data class HomeViewModelState(
    val isLoadUpcoming: Boolean = true,
    val isLoadPopular: Boolean = true,
    val isLoadTopRate: Boolean = true,
    val upcomingMovies: List<Movie> = emptyList(),
    val popularMovies: List<Movie> = emptyList(),
    val topRateMovies: List<Movie> = emptyList(),
) {
    fun toUiState(): HomeViewModelState =
        HomeViewModelState(
                isLoadUpcoming = isLoadUpcoming,
                isLoadPopular = isLoadPopular,
                isLoadTopRate = isLoadTopRate,
                popularMovies = popularMovies,
                upcomingMovies = upcomingMovies,
                topRateMovies = topRateMovies,
            )
}

class HomeViewModel: ViewModel() {

    private val viewModelState = MutableStateFlow(HomeViewModelState())
    private var repository: MovieRepositoryImpl = MovieRepositoryImpl()

    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun getMovieList() {
        getMovies(CategoryType.upcoming)
        getMovies(CategoryType.popular)
        getMovies(CategoryType.toprate)
    }

    private fun getMovies(type: CategoryType) {
        viewModelScope.launch {
            val response = repository.getMovies(GroupType.movie, type, 1)
            viewModelState.update {
                when(response) {
                    is ResultWrapper.Success -> {
                        val dt = response.value.results!!
                        when(type) {
                            CategoryType.upcoming -> it.copy(isLoadUpcoming = false, upcomingMovies = dt)
                            CategoryType.popular -> it.copy(isLoadPopular = false, popularMovies = dt)
                            CategoryType.toprate -> it.copy(isLoadTopRate = false, topRateMovies = dt)
                            else ->  it.copy(isLoadUpcoming = false, isLoadPopular = false, isLoadTopRate = false)
                        }
                    }
                    is ResultWrapper.GenericError -> it.copy(isLoadUpcoming = false, isLoadPopular = false, isLoadTopRate = false)
                    is ResultWrapper.NetworkError -> it.copy(isLoadUpcoming = false, isLoadPopular = false, isLoadTopRate = false)
                }
            }

        }
    }

}
