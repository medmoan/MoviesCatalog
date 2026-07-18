package com.example.netflixclone.movies.data.remote.dto

data class MovieVideoDto(
    val results: List<Video>
)

data class Video(
    val key: String,
    val name: String,
    val site: String,
    val type: String
)