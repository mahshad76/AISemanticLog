package com.example.home.data.datasource.remote

import com.example.model.LogsResponseDto
import retrofit2.Response

internal interface LogRemoteDatasource {
    /**
     * Returns log
     */
    suspend fun fetchLogs(): Response<LogsResponseDto>
}