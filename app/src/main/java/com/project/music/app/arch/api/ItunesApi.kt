package com.project.music.app.arch.api
import com.project.music.app.responses.SearchResponse
import retrofit2.http.*

interface ItunesApi {
    companion object {
        // const val BASE_URL = "http://127.0.0.1:8000/"
        const val BASE_URL = "https://itunes.apple.com"

        const val SEARCH = "search"

    }

    @GET(SEARCH)
    suspend fun search(
        @Query("term") term: String,
        @Query("limit") limit: Int = 20,
    ): SearchResponse

}