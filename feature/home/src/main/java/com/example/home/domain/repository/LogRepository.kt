package com.example.home.domain.repository

import com.example.home.domain.model.Log

internal interface LogRepository {
    /**
     * Returns list of logs
     */
    suspend fun fetchLogs(): Result<List<Log>>
}