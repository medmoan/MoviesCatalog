package com.example.netflixclone.movies.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieResultDto(
    val page: Int,
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)