package com.example.netflixclone.core.di

import android.content.Context
import androidx.room.Room
import com.example.netflixclone.core.database.AppDatabase
import com.example.netflixclone.movies.data.local.room.FavoriteMovieDao
import com.example.netflixclone.movies.data.local.room.SearchHistoryDao
import com.example.netflixclone.movies.data.local.room.DiscoverMovieDao
import com.example.netflixclone.movies.data.local.room.MovieDetailDao
import com.example.netflixclone.movies.data.local.room.TrendingMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "netflix_db"
        ).build()
    }

    @Provides
    fun provideDiscoverMovieDao(
        database: AppDatabase
    ): DiscoverMovieDao = database.discoverMovieDao()

    @Provides
    fun provideTrendingMovieDao(
        database: AppDatabase
    ): TrendingMovieDao = database.trendingMovieDao()

    @Provides
    fun provideSearchHistoryDao(
        database: AppDatabase
    ): SearchHistoryDao = database.searchHistoryDao()

    @Provides
    fun provideFavoriteMovieDao(
        database: AppDatabase
    ): FavoriteMovieDao = database.favoriteMovieDao()

    @Provides
    fun provideMovieDetailDao(
        database: AppDatabase
    ): MovieDetailDao = database.movieDetailDao()





}