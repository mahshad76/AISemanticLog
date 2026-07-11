package com.example.home.domain.repository

import com.example.home.domain.model.Log

internal interface LogRepository {
    /**
     * Returns log
     */
    suspend fun fetchLogs(): Result<Log>
}