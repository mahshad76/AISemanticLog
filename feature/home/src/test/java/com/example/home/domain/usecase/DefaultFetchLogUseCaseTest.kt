package com.example.home.domain.usecase

import com.example.home.domain.model.Log
import com.example.home.domain.repository.LogRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DefaultFetchLogUseCaseTest {
    private val repository = mockk<LogRepository>()
    private lateinit var useCase: FetchLogUseCase

    @Before
    fun setUp() {
        useCase = DefaultFetchLogUseCase(
            logRepository = repository
        )
    }

    @Test
    fun `invoke calls fetchLogs on the repository`() = runTest {
        coEvery { repository.fetchLogs() } returns Result.success(Log.DEFAULT)

        useCase()

        coVerify(exactly = 1) { repository.fetchLogs() }
    }
}