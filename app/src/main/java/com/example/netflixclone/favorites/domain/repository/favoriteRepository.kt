package com.example.netflixclone.favorites.domain.repository

import com.example.netflixclone.movies.domain.model.Movie
import com.example.netflixclone.core.utils.Respond
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    suspend fun addToFavorites(
        movie: Movie
    )

    suspend fun removeFromFavorites(
        movie: Movie
    )

    suspend fun getFavoriteMovies():
            Flow<Respond<List<Movie>>>

    suspend fun isFavorite(
        movieId: Int
    ): Boolean

    suspend fun toggleFavorite(
        movie: Movie
    )
}