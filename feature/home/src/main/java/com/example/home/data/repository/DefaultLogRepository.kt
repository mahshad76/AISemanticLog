package com.example.home.data.repository

import com.example.common.R
import com.example.common.error.RepositoryError
import com.example.common.stringresolver.StringResolver
import com.example.home.data.datasource.remote.LogRemoteDatasource
import com.example.home.data.mapper.toLog
import com.example.home.domain.model.Log
import com.example.home.domain.repository.LogRepository
import com.example.threading.qualifiers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultLogRepository @Inject constructor(
    private val datasource: LogRemoteDatasource,
    private val stringResolver: StringResolver,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : LogRepository {
    override suspend fun fetchLogs(): Result<Log> {
        return withContext(ioDispatcher) {
            try {
                datasource.fetchLogs().let { response ->
                    if (response.isSuccessful) {
                        response.body()?.let { body ->
                            Result.success(body.toLog())
                        } ?: Result.failure(
                            RepositoryError.NoDataError(
                                stringResolver.findString(
                                    R.string.error_response_body_is_null
                                )
                            )
                        )
                    } else {
                        Result.failure(
                            RepositoryError.NetworkError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                Result.failure(RepositoryError.UnknownError(e))
            }
        }
    }
}