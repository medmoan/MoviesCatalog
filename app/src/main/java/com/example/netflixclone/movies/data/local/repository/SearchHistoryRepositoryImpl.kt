package com.example.netflixclone.movies.data.local.repository

import com.example.netflixclone.core.domain.model.SearchHistory
import com.example.netflixclone.core.domain.repository.SearchHistoryRepository
import com.example.netflixclone.core.utils.Respond
import com.example.netflixclone.movies.data.local.room.SearchHistoryDao
import com.example.netflixclone.movies.data.local.room.SearchHistoryEntity
import com.example.netflixclone.movies.data.mapper.toSearchHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchHistoryRepositoryImpl @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao
): SearchHistoryRepository {
    override suspend fun getSearchHistory(): Flow<Respond<List<SearchHistory>>> = flow {
        try {
            searchHistoryDao.getSearchHistory().collect { searchMovieEntity ->
                val searchList = searchMovieEntity.map { it.toSearchHistory() }
                emit(Respond.Success(searchList))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Respond.Error(e.message ?: "Unknown error"))
        }

    }

    override suspend fun insert(query: String) {
        val searchHistoryEntity = SearchHistoryEntity(
            q = query,
            searchedAt = System.currentTimeMillis()
        )
        searchHistoryDao.insert(searchHistoryEntity)
    }

    override suspend fun clear() {
        searchHistoryDao.clear()
    }

    override suspend fun delete(query: String) {
        searchHistoryDao.delete(query)
    }
}