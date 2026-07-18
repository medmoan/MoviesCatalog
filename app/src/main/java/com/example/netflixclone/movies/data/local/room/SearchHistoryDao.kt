package com.example.netflixclone.movies.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {

    @Query("SELECT * FROM search_history ORDER BY searchedAt DESC")
    fun getSearchHistory(): Flow<List<SearchHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(search: SearchHistoryEntity)

    @Query("DELETE FROM search_history")
    suspend fun clear()
    @Query("DELETE FROM search_history WHERE q = :query")
    suspend fun delete(query: String)
}