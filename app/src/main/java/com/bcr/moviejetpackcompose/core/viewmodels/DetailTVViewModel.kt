package com.bcr.moviejetpackcompose.core.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcr.moviejetpackcompose.core.model.Crew
import com.bcr.moviejetpackcompose.core.model.GroupType
import com.bcr.moviejetpackcompose.core.model.Movie
import com.bcr.moviejetpackcompose.core.network.ResultWrapper
import com.bcr.moviejetpackcompose.core.repositories.MovieRepositoryImpl
import kotlinx.coroutines.launch


class DetailTVViewModel: ViewModel() {

    var movie: Movie? by mutableStateOf(null)
    var similiarMovies: List<Movie> by mutableStateOf(listOf())
    var casts: List<Crew> by mutableStateOf(listOf())

    private var repository: MovieRepositoryImpl = MovieRepositoryImpl()

    fun getDetailMovie(data: Movie) {
        this.movie = data
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
                    movie = response.value
                }
                is ResultWrapper.GenericError -> {}
                is ResultWrapper.NetworkError -> {}
            }
        }
    }

    private fun getSimiliarMovie(id: Int) {
        viewModelScope.launch {
            when (val response = repository.getSimiliarMovie(GroupType.tv, id)) {
                is ResultWrapper.Success -> {
                    response.value.results?.let {
                        similiarMovies = it
                    }
                }
                is ResultWrapper.GenericError -> {}
                is ResultWrapper.NetworkError -> {}
            }
        }
    }

    private fun getCrewMovie(id: Int) {
        viewModelScope.launch {
            when (val response = repository.getCrewMovie(GroupType.tv, id)) {
                is ResultWrapper.Success -> {
                    response.value.cast?.let {
                        casts = it
                    }
                }
                is ResultWrapper.GenericError -> {}
                is ResultWrapper.NetworkError -> {}
            }
        }
    }

}