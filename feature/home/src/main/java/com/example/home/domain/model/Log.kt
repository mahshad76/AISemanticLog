package com.example.home.domain.model

data class Log(
    val totalCount: Int,
    val sessionId: String,
    val data: List<LogGroup>
)
