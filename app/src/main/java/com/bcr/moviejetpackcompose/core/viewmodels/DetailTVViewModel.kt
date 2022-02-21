package com.bcr.moviejetpackcompose.core.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcr.moviejetpackcompose.core.model.GroupType
import com.bcr.moviejetpackcompose.core.model.Movie
import com.bcr.moviejetpackcompose.core.network.ResultWrapper
import com.bcr.moviejetpackcompose.core.repositories.MovieRepositoryImpl
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailTVViewModel: ViewModel() {

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
            when (val response = repository.getDetailMovie(GroupType.tv, id)) {
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
            when (val response = repository.getSimiliarMovie(GroupType.tv, id)) {
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
            when (val response = repository.getCrewMovie(GroupType.tv, id)) {
                is ResultWrapper.Success -> {
                    response.value.cast?.let { dt ->
                        viewModelState.update { it.copy(casts = dt, isLoadCredit = false) }
                    }
                }
                is ResultWrapper.GenericError -> {}
                is ResultWrapper.NetworkError -> {}
            }
        }
    }

}