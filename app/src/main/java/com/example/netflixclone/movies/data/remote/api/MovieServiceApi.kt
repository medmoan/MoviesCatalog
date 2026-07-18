package com.example.netflixclone.movies.data.remote.api

import com.example.netflixclone.movies.data.remote.dto.MovieDetailDto
import com.example.netflixclone.movies.data.remote.dto.MovieResultDto
import com.example.netflixclone.movies.data.remote.dto.MovieVideoDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieServiceApi {

    @GET("discover/movie")
    suspend fun fetchDiscoverMovie(
        @Query("include_adult") includeAdult: Boolean,
        @Query("include_video") includeVideo: Boolean,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
    ): MovieResultDto

    @GET("search/movie")
    suspend fun fetchSearchMovie(
        @Query("include_adult") includeAdult: Boolean,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("query") query: String
    ): MovieResultDto

    @GET("trending/movie/{time_window}")
    suspend fun fetchTrendingMovie(
        @Path("time_window") timeWindow: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): MovieResultDto

    // curl --request GET \
//     --url 'https://api.themoviedb.org/3/movie/movie_id?language=en-US' \
//     --header 'accept: application/json'

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String
    ): MovieDetailDto

    @GET("movie/{movie_id}/videos")
    suspend fun fetchMovieVideos(
        @Path("movie_id") movieId: Int
    ): MovieVideoDto
}