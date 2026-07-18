package com.example.netflixclone.movies.data.mapper

import com.example.netflixclone.movies.data.local.room.DiscoverMovieEntity
import com.example.netflixclone.movies.data.local.room.FavoriteMovieEntity
import com.example.netflixclone.movies.data.local.room.TrendingMovieEntity
import com.example.netflixclone.movies.data.remote.dto.MovieResultDto
import com.example.netflixclone.movies.domain.model.MovieResult

fun MovieResultDto.toMovieResult(): MovieResult {
    return MovieResult(
        page = page,
        movies = results.map { it.toMovie(page) },
        totalPages = totalPages,
        totalResults = totalResults
    )
}
fun FavoriteMovieEntity.toMovieResult(): MovieResult {
    return MovieResult(
        movies = listOf(toMovie()),
    )
}
fun FavoriteMovieEntity.favoriteToMovieResult(): MovieResult {
    return MovieResult(
        movies = listOf(toMovie())
    )
}
@JvmName("discoverMovieResult")
fun List<DiscoverMovieEntity>.toMovieResult(page: Int): MovieResult {
    return MovieResult(
        page = page,
        movies = map { it.toMovie() },
        totalPages = 1000
    )
}
@JvmName("trendingMovieResult")
fun List<TrendingMovieEntity>.toMovieResult(page: Int): MovieResult {
    return MovieResult(
        page = page,
        movies = map { it.toMovie() },
        totalPages = 1000
    )
}