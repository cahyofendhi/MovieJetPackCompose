package com.bcr.moviejetpackcompose.core.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcr.moviejetpackcompose.core.model.CategoryType
import com.bcr.moviejetpackcompose.core.model.GroupType
import com.bcr.moviejetpackcompose.core.model.Movie
import com.bcr.moviejetpackcompose.core.network.ResultWrapper
import com.bcr.moviejetpackcompose.core.repositories.MovieRepositoryImpl
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    var upcomingMovies: List<Movie> by mutableStateOf(listOf())
    var popularMovies: List<Movie> by mutableStateOf(listOf())
    var topMovies: List<Movie> by mutableStateOf(listOf())

    private var repository: MovieRepositoryImpl = MovieRepositoryImpl()

    fun getMovieList() {
        getMovies(CategoryType.upcoming)
        getMovies(CategoryType.popular)
        getMovies(CategoryType.toprate)
    }

    private fun getMovies(type: CategoryType) {
        viewModelScope.launch {
            when (val response = repository.getMovies(GroupType.movie, type, 1)) {
                is ResultWrapper.Success -> {
                    response.value.results?.let {
                        when (type) {
                            CategoryType.upcoming -> upcomingMovies = it
                            CategoryType.popular -> popularMovies = it
                            CategoryType.toprate -> topMovies = it
                            else -> {}
                        }
                    }
                }
                is ResultWrapper.GenericError -> {}
                is ResultWrapper.NetworkError -> {}
            }
        }
    }

}
