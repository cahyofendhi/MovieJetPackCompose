package com.bcr.moviejetpackcompose.core.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcr.moviejetpackcompose.core.model.Movie
import com.bcr.moviejetpackcompose.core.network.ResultWrapper
import com.bcr.moviejetpackcompose.core.repositories.MovieRepositoryImpl
import kotlinx.coroutines.launch
import com.bcr.moviejetpackcompose.core.model.Crew
import com.bcr.moviejetpackcompose.core.model.GroupType
import kotlinx.coroutines.flow.*

data class DetailViewModelState(
    val movie: Movie? = null,
    val isLoadCredit: Boolean = true,
    val isLoadSimiliar: Boolean = true,
    val similiarMovies: List<Movie> = emptyList(),
    val casts: List<Crew> = emptyList(),
) {
    fun toUiState(): DetailViewModelState =
        DetailViewModelState(
            movie = movie,
            isLoadCredit = isLoadCredit,
            isLoadSimiliar = isLoadSimiliar,
            similiarMovies = similiarMovies,
            casts = casts,
        )
}

class DetailMovieViewModel: ViewModel() {

    private var repository: MovieRepositoryImpl = MovieRepositoryImpl()
    private val viewModelState = MutableStateFlow(DetailViewModelState())

    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun getDetailMovie(data: Movie) {
        viewModelState.update { it.copy(movie = data) }
        data.id?.let { id ->
            getMovie(id)
            getCrewMovie(id)
            getSimiliarMovie(id)
        }
    }

    private fun getMovie(id: Int) {
        viewModelScope.launch {
            val response = repository.getDetailMovie(GroupType.movie, id)
            when (response) {
                is ResultWrapper.Success -> {
                    viewModelState.update { it.copy(movie = response.value) }
                }
                is ResultWrapper.GenericError -> {}
                is ResultWrapper.NetworkError -> {}
            }
        }
    }

    private fun getSimiliarMovie(id: Int) {
        viewModelState.update { it.copy(isLoadSimiliar = true) }
        viewModelScope.launch {
            when (val response = repository.getSimiliarMovie(GroupType.movie, id)) {
                is ResultWrapper.Success -> {
                    response.value.results?.let { dt ->
                        viewModelState.update { it.copy(isLoadSimiliar = false, similiarMovies = dt) }
                    }
                }
                is ResultWrapper.GenericError -> {}
                is ResultWrapper.NetworkError -> {}
            }
        }
    }

    private fun getCrewMovie(id: Int) {
        viewModelState.update { it.copy(isLoadCredit = true) }
        viewModelScope.launch {
            when (val response = repository.getCrewMovie(GroupType.movie, id)) {
                is ResultWrapper.Success -> {
                    response.value.cast?.let { dt ->
                        viewModelState.update { it.copy(isLoadCredit = false, casts = dt) }
                    }
                }
                is ResultWrapper.GenericError -> {}
                is ResultWrapper.NetworkError -> {}
            }
        }
    }

}