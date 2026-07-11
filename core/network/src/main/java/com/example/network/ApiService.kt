package com.example.network

import com.example.model.LogsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val LOGS_ENDPOINT =
    "v0/b/fieldinspectiondev.firebasestorage.app/o/data%2Flogs_5k.json"

private const val ALT_QUERY = "alt"
private const val TOKEN_QUERY = "token"
private const val MEDIA_QUERY_VALUE = "media"

interface ApiService {

    @GET(LOGS_ENDPOINT)
    suspend fun fetchLogs(
        @Query(ALT_QUERY)
        alt: String = MEDIA_QUERY_VALUE,

        @Query(TOKEN_QUERY)
        token: String = BuildConfig.API_KEY
    ): Response<List<LogsResponseDto>>
}