package com.example.netflixclone.movies.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDetailDao {
    @Query("SELECT * FROM movies_detail WHERE id = :movieId")
    suspend fun getMovieDetail(movieId: Int): MovieDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(movieDetail: MovieDetailEntity)

    @Query("DELETE FROM movies_detail WHERE id = :movieId")
    suspend fun removeMovieDetail(movieId: Int)

    @Query("""
SELECT MIN(cachedAt)
FROM popular_movies
""")
    suspend fun getCacheTime(): Long?
}