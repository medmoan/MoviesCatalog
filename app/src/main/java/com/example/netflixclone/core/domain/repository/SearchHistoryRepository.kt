package com.example.netflixclone.core.domain.repository

import com.example.netflixclone.core.domain.model.SearchHistory
import com.example.netflixclone.core.utils.Respond
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    suspend fun getSearchHistory(): Flow<Respond<List<SearchHistory>>>
    suspend fun insert(query: String)
    suspend fun clear()
    suspend fun delete(query: String)
}