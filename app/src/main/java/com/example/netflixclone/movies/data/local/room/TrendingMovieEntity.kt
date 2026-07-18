package com.example.netflixclone.movies.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trending_movies")
data class TrendingMovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val rating: Double,
    val page: Int,
    val cachedAt: Long = System.currentTimeMillis()
)