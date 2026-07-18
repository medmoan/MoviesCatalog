package com.example.netflixclone.core.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.netflixclone.movies.data.local.room.FavoriteMovieDao
import com.example.netflixclone.movies.data.local.room.SearchHistoryDao
import com.example.netflixclone.movies.data.local.repository.SearchHistoryRepositoryImpl
import com.example.netflixclone.core.domain.repository.SearchHistoryRepository
import com.example.netflixclone.favorites.data.local.repository.FavoriteRepositoryImpl
import com.example.netflixclone.favorites.domain.repository.FavoriteRepository
import com.example.netflixclone.movies.data.local.room.DiscoverMovieDao
import com.example.netflixclone.movies.data.local.room.MovieDetailDao
import com.example.netflixclone.movies.data.local.room.TrendingMovieDao
import com.example.netflixclone.movies.data.remote.api.MovieServiceApi
import com.example.netflixclone.movies.data.local.repository.MovieRepositoryImpl
import com.example.netflixclone.movies.domain.repository.MovieRepository
import com.example.netflixclone.core.datastore.SettingsRepositoryImpl
import com.example.netflixclone.settings.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {
    @Provides
    @Singleton
    fun provideMovieRepository(
        movieServiceApi: MovieServiceApi,
        settingsRepository: SettingsRepository,
        discoverMovieDao: DiscoverMovieDao,
        trendingMovieDao: TrendingMovieDao,
        movieDetailDao: MovieDetailDao
    ): MovieRepository {
        return MovieRepositoryImpl(
            movieServiceApi = movieServiceApi,
            settingsRepository = settingsRepository,
            discoverMovieDao = discoverMovieDao,
            trendingMovieDao = trendingMovieDao,
            movieDetailDao = movieDetailDao
        )
    }

    @Provides
    @Singleton
    fun provideFavoriteMovieRepository(
        favoriteMovieDao: FavoriteMovieDao
    ): FavoriteRepository {
        return FavoriteRepositoryImpl(
           favoriteMovieDao = favoriteMovieDao
        )
    }
    @Provides
    @Singleton
    fun provideSettingsRepository(
        dataStore: DataStore<Preferences>
    ): SettingsRepository {
        return SettingsRepositoryImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideSearchHistoryRepository(
        searchHistoryDao: SearchHistoryDao
    ): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(searchHistoryDao)
    }
}