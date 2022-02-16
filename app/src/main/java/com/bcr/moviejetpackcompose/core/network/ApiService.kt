package com.bcr.moviejetpackcompose.core.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import com.bcr.moviejetpackcompose.core.data.API_KEY
import com.bcr.moviejetpackcompose.core.data.BASE_URL
import com.bcr.moviejetpackcompose.core.model.CrewResponse
import com.bcr.moviejetpackcompose.core.model.Movie
import com.bcr.moviejetpackcompose.core.model.MovieResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("{group}/{category}")
    suspend fun getMovieList(
        @Path("group") group: String,
        @Path("category") category: String,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1,
    ): MovieResponse

    @GET("{group}/{id}")
    suspend fun getDetailMovie(
        @Path("group") group: String,
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): Movie

    @GET("{group}/{id}/similar")
    suspend fun getSimiliarMovie(
        @Path("group") group: String,
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): MovieResponse

    @GET("{group}/{id}/credits")
    suspend fun getCrewMovie(
        @Path("group") group: String,
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): CrewResponse


    companion object {
        var apiService: ApiService? = null
        fun getInstance() : ApiService {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient = OkHttpClient.Builder().connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .addInterceptor(loggingInterceptor)
                .build()
            val gson = GsonBuilder()
                .setLenient()
                .create()
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(ApiService::class.java)
            }
            return apiService!!
        }
    }

}
