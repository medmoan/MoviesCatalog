package com.example.netflixclone.movies.data.mapper

import com.example.netflixclone.core.domain.model.SearchHistory
import com.example.netflixclone.movies.data.local.room.SearchHistoryEntity

fun SearchHistoryEntity.toSearchHistory(): SearchHistory {
    return SearchHistory(
        id = id,
        query = q,
        searchedAt = searchedAt
    )
}
fun SearchHistory.searchHistoryEntity(): SearchHistoryEntity {
    return SearchHistoryEntity(
        q = query,
        searchedAt = searchedAt
    )
}