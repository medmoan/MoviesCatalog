package com.example.netflixclone.movies.data.local.repository


import com.example.netflixclone.core.utils.CacheConstants
import com.example.netflixclone.movies.data.local.room.DiscoverMovieDao
import com.example.netflixclone.movies.data.local.room.MovieDetailDao
import com.example.netflixclone.movies.data.local.room.TrendingMovieDao
import com.example.netflixclone.movies.data.mapper.toDiscoverMovieEntity
import com.example.netflixclone.movies.data.mapper.toMovieDetail
import com.example.netflixclone.movies.data.mapper.toMovieDetailEntity
import com.example.netflixclone.movies.data.remote.api.MovieServiceApi
import com.example.netflixclone.movies.data.mapper.toMovieResult
import com.example.netflixclone.movies.data.mapper.toTrendingMovieEntity
import com.example.netflixclone.movies.domain.model.MovieDetail
import com.example.netflixclone.movies.domain.model.MovieResult
import com.example.netflixclone.movies.domain.params.DiscoverMovieParams
import com.example.netflixclone.movies.domain.repository.MovieRepository
import com.example.netflixclone.settings.domain.repository.SettingsRepository
import com.example.netflixclone.core.utils.Respond
import com.example.netflixclone.core.utils.rethrowIfCancellation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieServiceApi: MovieServiceApi,
    private val settingsRepository: SettingsRepository,
    private val discoverMovieDao: DiscoverMovieDao,
    private val trendingMovieDao: TrendingMovieDao,
    private val movieDetailDao: MovieDetailDao
): MovieRepository {
    companion object {
        private const val FIRST_PAGE = 1
    }
    private suspend fun getSettings() = settingsRepository.getSettings()
    private suspend fun getLanguage() = getSettings().language
    private suspend fun getIncludeAdult() = getSettings().includeAdult

    override suspend fun fetchDiscoverMovie(
        page: Int
    ): Flow<Respond<MovieResult>> = flow {
        try {
            val discoverMovieParams = DiscoverMovieParams()
            emit(Respond.Loading)
            val cacheTime = discoverMovieDao.getCacheTime()?: 0L

            val isExpired = System.currentTimeMillis() - cacheTime >
                    CacheConstants.POPULAR_MOVIES
            if (!isExpired) {
                val cached = discoverMovieDao.getPopularMovies(page).toMovieResult(page)
                if (cached.movies.isNotEmpty()) {
                    emit(Respond.Success(cached))
                    return@flow
                }
            }
            val result = movieServiceApi.fetchDiscoverMovie(
                includeAdult = getIncludeAdult(),
                includeVideo = discoverMovieParams.includeVideo,
                language = getLanguage(),
                page = page,
                sortBy = discoverMovieParams.sortBy
            ).toMovieResult()
            if (page == FIRST_PAGE) {
                discoverMovieDao.clearPopularMovies()
            }
            discoverMovieDao.insertMovies(
                result.movies.map { it.toDiscoverMovieEntity() }
            )
            emit(Respond.Success(result))
        } catch (e: Exception) {
            e.rethrowIfCancellation()
            e.printStackTrace()
            emit(Respond.Error(e.message ?: "An unknown error occurred."))
        }
    }

    override suspend fun fetchSearchMovie(
        query: String,
        page: Int
    ): Flow<Respond<MovieResult>> = flow {

        try {
            emit(Respond.Loading)
            val result = movieServiceApi.fetchSearchMovie(
                includeAdult = getIncludeAdult(),
                language = getLanguage(),
                page = page,
                query = query
            ).toMovieResult()
            emit(Respond.Success(result))
        } catch (e: Exception) {
            e.rethrowIfCancellation()
            e.printStackTrace()
            emit(Respond.Error(e.message ?: "An unknown error occurred."))
        }
    }

    override suspend fun fetchTrendingMovie(
        timeWindow: String,
        page: Int
    ): Flow<Respond<MovieResult>> = flow {
        try {
            emit(Respond.Loading)
            val cacheTime = trendingMovieDao.getCacheTime()?: 0L

            val isExpired = System.currentTimeMillis() - cacheTime >
                    CacheConstants.TRENDING_MOVIES
            if (cacheTime > 0L && !isExpired) {
                val cached = trendingMovieDao.getTrendingMovies(page).toMovieResult(page)
                if (cached.movies.isNotEmpty()) {
                    emit(Respond.Success(cached))
                    return@flow
                }
            }
            val result = movieServiceApi.fetchTrendingMovie(
                timeWindow = timeWindow,
                language = getLanguage(),
                page = page
            ).toMovieResult()
            if (page == FIRST_PAGE) {
                trendingMovieDao.clearTrendingMovies()
            }
            trendingMovieDao.insertMovies(
                result.movies.map { it.toTrendingMovieEntity() }
            )
            emit(Respond.Success(result))

        } catch (e: Exception) {
            e.rethrowIfCancellation()
            e.printStackTrace()
            emit(Respond.Error(e.message ?: "An unknown error occurred."))
        }
    }

    override suspend fun getMovieDetail(movieId: Int): Flow<Respond<MovieDetail>> = flow {
        try {
            emit(Respond.Loading)
            val cacheTime = movieDetailDao.getCacheTime()?: 0L

            val isExpired = System.currentTimeMillis() - cacheTime >
                    CacheConstants.MOVIE_DETAILS
            if (cacheTime > 0L && !isExpired) {
                val cached = movieDetailDao.getMovieDetail(movieId)?.toMovieDetail()
                if (cached != null) {
                    emit(Respond.Success(cached))
                    return@flow
                }
                val resDetail = movieServiceApi.getMovieDetail(
                    movieId = movieId,
                    language = getLanguage()
                )

                val videoKey = try {
                    movieServiceApi
                        .fetchMovieVideos(movieId)
                        .results
                        .firstOrNull {
                            it.type == "Trailer" &&
                                    it.site == "YouTube"
                        }?.key
                } catch (e: Exception) {
                    e.rethrowIfCancellation()
                    null
                }
                val movieDetail = resDetail.toMovieDetail(videoKey)
                movieDetailDao.removeMovieDetail(movieId)
                movieDetailDao.insertMovieDetail(movieDetail.toMovieDetailEntity())
                emit(Respond.Success(movieDetail))

            }
        }  catch (e: Exception) {
            e.rethrowIfCancellation()
            e.printStackTrace()
            emit(Respond.Error(e.message ?: "An unknown error occurred."))
        }
    }
}