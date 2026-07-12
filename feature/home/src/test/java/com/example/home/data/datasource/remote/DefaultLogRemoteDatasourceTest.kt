package com.example.home.data.datasource.remote

import com.example.model.LogsResponseDto
import com.example.network.ApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class DefaultLogRemoteDatasourceTest {
    private val apiService = mockk<ApiService>()
    private lateinit var datasource: LogRemoteDatasource

    @Before
    fun setUp() {
        datasource = DefaultLogRemoteDatasource(apiService = apiService)
    }

    @Test
    fun `fetchLogs calls apiService_fetchLogs once`() = runTest {
        coEvery { apiService.fetchLogs() } returns Response.success(LogsResponseDto())

        datasource.fetchLogs()

        coVerify(exactly = 1) { apiService.fetchLogs() }
    }
}