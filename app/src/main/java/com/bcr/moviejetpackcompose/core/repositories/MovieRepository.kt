package com.bcr.moviejetpackcompose.core.repositories

import com.bcr.moviejetpackcompose.core.model.*
import com.bcr.moviejetpackcompose.core.network.ApiService
import com.bcr.moviejetpackcompose.core.network.ResultWrapper
import com.bcr.moviejetpackcompose.core.network.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface MovieRepository {
    suspend fun getMovies(group: GroupType, category: CategoryType, page: Int): ResultWrapper<MovieResponse>

    suspend fun getDetailMovie(group: GroupType, id: Int): ResultWrapper<Movie>

    suspend fun getSimiliarMovie(group: GroupType, id: Int): ResultWrapper<MovieResponse>

    suspend fun getCrewMovie(group: GroupType, id: Int): ResultWrapper<CrewResponse>

}

class MovieRepositoryImpl(private val service: ApiService = ApiService.getInstance(),
                          private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : MovieRepository {

    override suspend fun getMovies(
        group: GroupType,
        category: CategoryType,
        page: Int
    ): ResultWrapper<MovieResponse> {
        return safeApiCall(dispatcher) { service.getMovieList(group = group.type ,category = category.type, page = page) }
    }

    override suspend fun getDetailMovie(group: GroupType, id: Int): ResultWrapper<Movie> {
        return safeApiCall(dispatcher) { service.getDetailMovie(group.type, id) }
    }

    override suspend fun getSimiliarMovie(group: GroupType, id: Int): ResultWrapper<MovieResponse> {
        return safeApiCall(dispatcher) { service.getSimiliarMovie(group.type, id) }
    }

    override suspend fun getCrewMovie(group: GroupType, id: Int): ResultWrapper<CrewResponse> {
        return safeApiCall(dispatcher) { service.getCrewMovie(group.type, id) }
    }

}