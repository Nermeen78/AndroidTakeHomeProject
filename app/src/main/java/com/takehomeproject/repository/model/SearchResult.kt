package com.takehomeproject.repository.model

data class SearchResult(
    var resultCount: Int,
    var results: List<ITunesContent>
)