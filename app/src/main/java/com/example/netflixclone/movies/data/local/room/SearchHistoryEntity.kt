package com.example.netflixclone.movies.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val q: String,
    val searchedAt: Long
)