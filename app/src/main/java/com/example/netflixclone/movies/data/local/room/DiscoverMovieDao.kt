package com.example.netflixclone.movies.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DiscoverMovieDao {

    @Query("SELECT * FROM popular_movies WHERE page = :page")
    suspend fun getPopularMovies(page: Int): List<DiscoverMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<DiscoverMovieEntity>)

    @Query("DELETE FROM popular_movies")
    suspend fun clearPopularMovies()

    @Query("""
SELECT MIN(cachedAt)
FROM popular_movies
""")
    suspend fun getCacheTime(): Long?

}
