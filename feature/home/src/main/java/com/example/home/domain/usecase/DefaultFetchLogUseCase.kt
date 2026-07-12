package com.example.home.domain.usecase

import com.example.home.domain.repository.LogRepository
import javax.inject.Inject

internal class DefaultFetchLogUseCase @Inject constructor(private val logRepository: LogRepository) :
    FetchLogUseCase {
    override suspend fun invoke() = logRepository.fetchLogs()
}