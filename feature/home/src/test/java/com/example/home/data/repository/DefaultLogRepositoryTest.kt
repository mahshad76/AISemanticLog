package com.example.home.data.repository

import com.example.common.error.RepositoryError
import com.example.common.stringresolver.StringResolver
import com.example.home.data.datasource.remote.LogRemoteDatasource
import com.example.home.domain.repository.LogRepository
import com.example.model.LogsResponseDto
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class DefaultLogRepositoryTest {
    private val datasource = mockk<LogRemoteDatasource>()
    private val dispatcher = StandardTestDispatcher()
    private val stringResolver = mockk<StringResolver>()
    private lateinit var repository: LogRepository

    @Before
    fun setUp() {
        repository = DefaultLogRepository(
            datasource = datasource,
            ioDispatcher = dispatcher,
            stringResolver = stringResolver
        )
    }

    @Test
    fun `fetchLogs returns NetworkError on unsuccessful HTTP response`() =
        runTest(dispatcher.scheduler) {
            val errorCode = 404
            val errorBody = "{\"error\": \"Not Found\"}"
            val errorResponse = Response.error<LogsResponseDto>(
                errorCode,
                errorBody.toResponseBody("application/json".toMediaTypeOrNull())
            )
            coEvery { datasource.fetchLogs() } returns errorResponse

            val result = repository.fetchLogs()

            assertThat(result.isFailure).isTrue()
            val failure = result.exceptionOrNull()
            assertThat(failure).isInstanceOf(RepositoryError.NetworkError::class.java)
            val networkError = failure as RepositoryError.NetworkError
            assertThat(networkError.code).isEqualTo(errorCode)
            assertThat(networkError.message).isEqualTo(errorBody)
        }

    @Test
    fun `fetchLogs returns NoDataError when response body is null`() =
        runTest(dispatcher.scheduler) {
            val expectedErrorMessage = "Response body is null"
            coEvery { datasource.fetchLogs() } returns Response.success(null)
            coEvery { stringResolver.findString(any()) } returns expectedErrorMessage

            val result = repository.fetchLogs()

            assertThat(result.isFailure).isTrue()
            val failure = result.exceptionOrNull()
            assertThat(failure).isInstanceOf(RepositoryError.NoDataError::class.java)
            val noDataError = failure as RepositoryError.NoDataError
            assertThat(noDataError.message).isEqualTo(expectedErrorMessage)
        }

    @Test
    fun `fetchLogs returns UnknownError on unexpected exception`() =
        runTest(dispatcher.scheduler) {
            val expectedErrorMessage = "Unexpected error during network call"
            val exception = Exception(expectedErrorMessage)
            coEvery { datasource.fetchLogs() } throws exception

            val result = repository.fetchLogs()

            assertThat(result.isFailure).isTrue()
            val failure = result.exceptionOrNull()
            assertThat(failure).isInstanceOf(RepositoryError.UnknownError::class.java)
            val unknownError = failure as RepositoryError.UnknownError
            assertThat(unknownError.cause?.message).isEqualTo(expectedErrorMessage)
        }
}