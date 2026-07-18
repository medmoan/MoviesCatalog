package com.example.netflixclone.movies.data.mapper

import com.example.netflixclone.movies.data.local.room.MovieDetailEntity
import com.example.netflixclone.movies.data.remote.dto.MovieDetailDto
import com.example.netflixclone.movies.domain.model.MovieDetail

fun MovieDetailEntity.toMovieDetail(): MovieDetail {
    return MovieDetail(
        id = id,
        title = title,
        originalTitle = originalTitle,
        overview = overview,
        tagline = tagline?: "",
        posterPath = posterPath?: "",
        backdropPath = backdropPath?: "",
        releaseDate = releaseDate,
        runtime = runtime,
        voteAverage = voteAverage,
        voteCount = voteCount,
        popularity = popularity,
        genres = genres,
        budget = budget,
        productionCompanies = productionCompanies,
        status = status
    )
}
fun MovieDetailDto.toMovieDetail(videoKey: String?): MovieDetail {
    return MovieDetail(
        id = id,
        title = title,
        originalTitle = originalTitle ?: "",
        overview = overview,
        tagline = tagline ?: "",
        backdropPath = backdropPath ?: "",
        releaseDate = releaseDate ?: "",
        posterPath = posterPath ?: "",
        runtime = runtime ?: 0,
        voteAverage = voteAverage,
        voteCount = voteCount,
        popularity = popularity,
        budget = budget,
        genres = genres.joinToString { it.name },
        productionCompanies = productionCompanies?.joinToString { it.name } ?: "",
        status = status ?: "",
        videoKey = videoKey
    )
}
fun MovieDetail.toMovieDetailEntity(): MovieDetailEntity {
    return MovieDetailEntity(
        id = id,
        title = title,
        originalTitle = originalTitle,
        overview = overview,
        tagline = tagline,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        posterPath = posterPath,
        runtime = runtime,
        voteAverage = voteAverage,
        voteCount = voteCount,
        popularity = popularity,
        genres = genres,
        status = status,
        budget = budget,
        productionCompanies = productionCompanies,
        cachedAt = cachedAt
    )
}