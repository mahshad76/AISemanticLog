package com.example.home.data.datasource.remote

import com.example.model.LogsResponseDto
import com.example.network.ApiService
import retrofit2.Response
import javax.inject.Inject

internal class DefaultLogRemoteDatasource @Inject constructor(private val apiService: ApiService) :
    LogRemoteDatasource {
    override suspend fun fetchLogs(): Response<List<LogsResponseDto>> = apiService.fetchLogs()
}