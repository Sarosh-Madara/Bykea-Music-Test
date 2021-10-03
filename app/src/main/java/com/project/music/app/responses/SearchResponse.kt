package com.project.music.app.responses

data class SearchResponse(
    val resultCount: Int,
    val results: List<Result>
)