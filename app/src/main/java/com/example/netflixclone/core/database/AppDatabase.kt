package com.example.netflixclone.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.netflixclone.movies.data.local.room.FavoriteMovieDao
import com.example.netflixclone.movies.data.local.room.FavoriteMovieEntity
import com.example.netflixclone.movies.data.local.room.SearchHistoryDao
import com.example.netflixclone.movies.data.local.room.SearchHistoryEntity
import com.example.netflixclone.movies.data.local.room.DiscoverMovieDao
import com.example.netflixclone.movies.data.local.room.DiscoverMovieEntity
import com.example.netflixclone.movies.data.local.room.MovieDetailDao
import com.example.netflixclone.movies.data.local.room.MovieDetailEntity
import com.example.netflixclone.movies.data.local.room.TrendingMovieDao
import com.example.netflixclone.movies.data.local.room.TrendingMovieEntity

@Database(
    entities = [
        FavoriteMovieEntity::class,
        SearchHistoryEntity::class,
        DiscoverMovieEntity::class,
        TrendingMovieEntity::class,
        MovieDetailEntity::class
               ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun discoverMovieDao(): DiscoverMovieDao
    abstract fun trendingMovieDao(): TrendingMovieDao

    abstract fun movieDetailDao(): MovieDetailDao
}