package com.example.netflixclone.movies.domain.repository

import com.example.netflixclone.movies.domain.model.MovieDetail
import com.example.netflixclone.movies.domain.model.MovieResult
import com.example.netflixclone.core.utils.Respond
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun fetchDiscoverMovie(
        page: Int
    ): Flow<Respond<MovieResult>>

    suspend fun fetchSearchMovie(
        query: String,
        page: Int
    ): Flow<Respond<MovieResult>>

    suspend fun fetchTrendingMovie(
        timeWindow: String,
        page: Int
    ): Flow<Respond<MovieResult>>

    suspend fun getMovieDetail(
        movieId: Int
    ): Flow<Respond<MovieDetail>>
}