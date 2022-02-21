package com.bcr.moviejetpackcompose.core.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcr.moviejetpackcompose.core.model.GroupType
import com.bcr.moviejetpackcompose.core.model.Movie
import com.bcr.moviejetpackcompose.core.network.ResultWrapper
import com.bcr.moviejetpackcompose.core.repositories.MovieRepositoryImpl
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class SearchViewModelState(
    val movies: List<Movie> = emptyList(),
    val page: Int = 1,
    val nextPage: Int = 1,
    val totPage: Int = 0,
    val keyword: String = "",
    val isRefresh: Boolean = false,
    val isLoading: Boolean = false,
) {
    fun toUiState(): SearchViewModelState = SearchViewModelState(
        movies = movies,
        page = page,
        totPage = totPage,
        keyword = keyword,
        isRefresh = isRefresh,
        isLoading = isLoading,
    )
}

class SearchViewModel(val type: String) : ViewModel() {
    private var repository: MovieRepositoryImpl = MovieRepositoryImpl()

    private val viewModelState = MutableStateFlow(SearchViewModelState())

    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun searchMovies(isRefresh: Boolean = false, keyword: String?) {
        viewModelState.update { it.copy(keyword = keyword ?: it.keyword,
            page = 1, nextPage = 1, totPage = 1, isRefresh = isRefresh) }
        getMovies()
    }

    fun getMovies() {
        if (viewModelState.value.nextPage > 1) {
            viewModelState.update { it.copy(isLoading = true) }
        }
        viewModelScope.launch {
            val response = repository.searchMovies(type, viewModelState.value.keyword, viewModelState.value.nextPage)
            when(response) {
                is ResultWrapper.Success -> {
                    var movies: List<Movie>
                    val movieResponse = response.value
                    viewModelState.update {
                        movies = if (it.page == movieResponse.page!!) {
                            movieResponse.results!!
                        } else {
                            it.movies + movieResponse.results!!
                        }
                        it.copy(movies = movies,
                            page = movieResponse.page,
                            nextPage = movieResponse.page + 1,
                            totPage = movieResponse.totalPages!!,
                            isLoading = false,
                            isRefresh = false,
                        )
                    }
                }
                else -> {}
            }
        }
    }

}
