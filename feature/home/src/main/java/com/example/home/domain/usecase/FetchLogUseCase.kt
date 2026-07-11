package com.example.home.domain.usecase

import com.example.home.domain.model.Log

internal interface FetchLogUseCase {
    suspend operator fun invoke(): Result<Log>
}