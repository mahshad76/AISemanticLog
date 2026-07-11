package com.example.model

import kotlinx.serialization.SerialName

data class LogMetadataDto(
    @SerialName("latency_ms")
    val latencyMs: Int? = null,
    @SerialName("is_ai_generated")
    val isAiGenerated: Boolean? = null
)
