package com.example.netflixclone.movies.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: FavoriteMovieEntity)

    @Delete
    suspend fun deleteMovie(movie: FavoriteMovieEntity)

    @Query("SELECT * FROM favorite_movies")
    fun getFavoriteMovies(): Flow<List<FavoriteMovieEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_movies WHERE id = :movieId)")
    suspend fun isFavorite(movieId: Int): Boolean
}