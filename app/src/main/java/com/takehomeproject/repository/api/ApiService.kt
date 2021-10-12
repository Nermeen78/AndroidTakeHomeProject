package com.takehomeproject.repository.api

import com.takehomeproject.repository.model.ITunesContent
import com.takehomeproject.repository.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val BASE_URL = "https://itunes.apple.com/"
    }

    @GET("/search")
    suspend fun getITunesContent(
        @Query("term") term: String,
        @Query("media") media: String
    ): SearchResult
}