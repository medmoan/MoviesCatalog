package com.example.netflixclone.movies.data.mapper

import com.example.netflixclone.movies.data.local.room.DiscoverMovieEntity
import com.example.netflixclone.movies.data.local.room.FavoriteMovieEntity
import com.example.netflixclone.movies.data.local.room.TrendingMovieEntity
import com.example.netflixclone.movies.domain.model.Movie
import com.example.netflixclone.movies.domain.model.MovieDetail
import com.example.netflixclone.movies.data.remote.dto.Result


fun TrendingMovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        page = page,
        rating = rating
    )
}
fun Movie.toDiscoverMovieEntity(): DiscoverMovieEntity {
    return DiscoverMovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        rating = rating,
        page = page
    )
}
fun Movie.toTrendingMovieEntity(): TrendingMovieEntity {
    return TrendingMovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        rating = rating,
        page = page
    )
}
fun Movie.toFavoriteMovieEntity(): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        id = id,
        title = title,
        overview = overview,
        rating = rating,
        posterPath = posterPath
    )
}
fun MovieDetail.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        page = 1,
        rating = voteAverage
    )
}
fun DiscoverMovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        page = page,
        rating = rating
    )
}
fun Result.toMovie(page: Int): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath ?: "",
        page = page,
        rating = voteAverage
    )
}

fun FavoriteMovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath ?: "",
        rating = rating,
        isFavorite = true
    )
}

