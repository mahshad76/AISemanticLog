package com.example.home.data.repository

import com.example.home.data.datasource.remote.LogRemoteDatasource
import com.example.home.data.mapper.toLog
import com.example.home.domain.model.Log
import com.example.home.domain.repository.LogRepository
import com.example.model.LogsResponseDto
import com.example.threading.qualifiers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultLogRepository @Inject constructor(
    private val datasource: LogRemoteDatasource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : LogRepository {
    override suspend fun fetchLogs(): Result<List<Log>> {
        return withContext(ioDispatcher) {
            try {
                datasource.fetchLogs().let { response ->
                    if (response.isSuccessful) {
                        response.body()?.let { body ->
                            Result.success(body.map(LogsResponseDto::toLog))
                        } ?: Result.failure(Exception(""))
                    } else {
                        TODO()
                    }
                }
            } catch (e: Exception) {
                TODO()
            }
        }
    }
}