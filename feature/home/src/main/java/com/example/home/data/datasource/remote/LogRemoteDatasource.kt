package com.example.home.data.datasource.remote

import com.example.model.LogsResponseDto
import retrofit2.Response

internal interface LogRemoteDatasource {
    /**
     * Returns list of logs
     */
    suspend fun fetchLogs(): Response<List<LogsResponseDto>>
}