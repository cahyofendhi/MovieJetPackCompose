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


data class TVViewModelState(
    val isLoadOnAir: Boolean = true,
    val isLoadPopular: Boolean = true,
    val isLoadTopRate: Boolean = true,
    val onAirMovies: List<Movie> = emptyList(),
    val popularMovies: List<Movie> = emptyList(),
    val topMovies: List<Movie> = emptyList(),
) {
    fun toUiState(): TVViewModelState =
        TVViewModelState(
            isLoadOnAir = isLoadOnAir,
            isLoadPopular = isLoadPopular,
            isLoadTopRate = isLoadTopRate,
            popularMovies = popularMovies,
            onAirMovies = onAirMovies,
            topMovies = topMovies,
        )
}


class TVViewModel: ViewModel() {

    private val viewModelState = MutableStateFlow(TVViewModelState())
    private var repository: MovieRepositoryImpl = MovieRepositoryImpl()

    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun getMovieList() {
        getMovies(CategoryType.onAir)
        getMovies(CategoryType.popular)
        getMovies(CategoryType.toprate)
    }

    private fun getMovies(type: CategoryType) {
        viewModelScope.launch {
            val response = repository.getMovies(GroupType.tv, type, 1)
            viewModelState.update {
                when(response) {
                    is ResultWrapper.Success -> {
                        val dt = response.value.results!!
                        when(type) {
                            CategoryType.onAir -> it.copy(isLoadOnAir = false, onAirMovies = dt)
                            CategoryType.popular -> it.copy(isLoadPopular = false, popularMovies = dt)
                            CategoryType.toprate -> it.copy(isLoadTopRate = false, topMovies = dt)
                            else ->  it.copy(isLoadOnAir = false, isLoadPopular = false, isLoadTopRate = false)
                        }
                    }
                    is ResultWrapper.GenericError -> it.copy(isLoadOnAir = false, isLoadPopular = false, isLoadTopRate = false)
                    is ResultWrapper.NetworkError -> it.copy(isLoadOnAir = false, isLoadPopular = false, isLoadTopRate = false)
                }
            }

        }
    }

}
