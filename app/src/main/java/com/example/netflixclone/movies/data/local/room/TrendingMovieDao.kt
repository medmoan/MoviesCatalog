package com.example.netflixclone.movies.data.local.room


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrendingMovieDao {

    @Query("SELECT * FROM trending_movies WHERE page = :page")
    suspend fun getTrendingMovies(page: Int): List<TrendingMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<TrendingMovieEntity>)

    @Query("DELETE FROM trending_movies")
    suspend fun clearTrendingMovies()

    @Query("""
SELECT MIN(cachedAt)
FROM trending_movies
""")
    suspend fun getCacheTime(): Long?

}
