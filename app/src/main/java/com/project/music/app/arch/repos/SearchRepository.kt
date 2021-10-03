package com.project.music.app.arch.repos

import com.project.music.app.arch.api.ItunesApi
import com.project.music.app.responses.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class SearchRepository @Inject constructor(val apiHelper: ItunesApi) {
    fun search(
        term: String,
        limit: Int = 200,
    ): Flow<SearchResponse> = flow {
        val response = apiHelper.search(term, limit)
        emit(response)
    }.flowOn(Dispatchers.IO)
}