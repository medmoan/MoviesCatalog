package com.example.netflixclone.favorites.data.local.repository

import com.example.netflixclone.movies.data.mapper.toMovie
import com.example.netflixclone.movies.data.local.room.FavoriteMovieDao
import com.example.netflixclone.favorites.domain.repository.FavoriteRepository
import com.example.netflixclone.movies.data.mapper.toFavoriteMovieEntity
import com.example.netflixclone.movies.domain.model.Movie
import com.example.netflixclone.core.utils.Respond
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao
) : FavoriteRepository {

    override suspend fun addToFavorites(
        movie: Movie
    ) {
        val movieEntity = movie.toFavoriteMovieEntity()
        favoriteMovieDao.insertMovie(movieEntity)
    }

    override suspend fun removeFromFavorites(
        movie: Movie
    ) {
        val movieEntity = movie.toFavoriteMovieEntity()
        favoriteMovieDao.deleteMovie(movieEntity)
    }

    override suspend fun getFavoriteMovies(): Flow<Respond<List<Movie>>> {
        return favoriteMovieDao.getFavoriteMovies()
            .map { entities ->
                val movies = entities.map { it.toMovie() }
                Respond.Success(movies) as Respond<List<Movie>>
            }
            .onStart {
                emit(Respond.Loading)
            }
            .catch { e ->
                e.printStackTrace()
                emit(Respond.Error(e.message.toString()))
            }
    }

    override suspend fun isFavorite(
        movieId: Int
    ): Boolean {
        return favoriteMovieDao.isFavorite(movieId)
    }

    override suspend fun toggleFavorite(
        movie: Movie
    ) {
        val movieEntity = movie.toFavoriteMovieEntity()
        if (favoriteMovieDao.isFavorite(movie.id)) {
            favoriteMovieDao.deleteMovie(movieEntity)
        } else {
            favoriteMovieDao.insertMovie(movieEntity)
        }
    }
}