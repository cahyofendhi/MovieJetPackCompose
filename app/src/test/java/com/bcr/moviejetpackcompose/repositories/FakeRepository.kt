package com.bcr.moviejetpackcompose.repositories

import com.bcr.moviejetpackcompose.core.model.*
import com.bcr.moviejetpackcompose.core.network.ResultWrapper
import com.bcr.moviejetpackcompose.core.repositories.MovieRepository
import com.bcr.moviejetpackcompose.resource.FakeResponse

class FakeRepository: MovieRepository {

    override suspend fun getMovies(
        group: GroupType,
        category: CategoryType,
        page: Int
    ): ResultWrapper<MovieResponse> {
        return ResultWrapper.Success(FakeResponse.dataMovies())
    }

    override suspend fun getDetailMovie(group: GroupType, id: Int): ResultWrapper<Movie> {
        val movies = FakeResponse.dataMovies().results
        val movie = movies?.get(0)
        return ResultWrapper.Success(movie!!)
    }

    override suspend fun getSimiliarMovie(group: GroupType, id: Int): ResultWrapper<MovieResponse> {
        val movies = FakeResponse.dataSimiliarMovies()
        return ResultWrapper.Success(movies)
    }

    override suspend fun getCrewMovie(group: GroupType, id: Int): ResultWrapper<CrewResponse> {
        val peoples = FakeResponse.dataCrewMovies()
        return ResultWrapper.Success(peoples)
    }

    override suspend fun searchMovies(
        group: String,
        keyword: String,
        page: Int
    ): ResultWrapper<MovieResponse> {
        return ResultWrapper.Success(FakeResponse.dataMovies())
    }


}