package com.example.home.domain.model

import com.example.model.LogEntryDto
import kotlinx.serialization.SerialName

data class Log(
    val totalCount: Int,
    val sessionId: String,
    val data: List<LogEntry>
)
