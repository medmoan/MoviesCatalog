package com.example.netflixclone.core.domain.model


data class SearchHistory(
    val id: Long = 0,
    val query: String,
    val searchedAt: Long
)