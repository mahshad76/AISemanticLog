package com.example.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogsResponseDto(
    @SerialName("total_count")
    val totalCount: Int? = null,
    @SerialName("session_id")
    val sessionId: String? = null,
    @SerialName("data")
    val data: List<LogEntryDto>? = null
)
