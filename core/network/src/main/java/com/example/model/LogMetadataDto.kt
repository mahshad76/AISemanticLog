package com.example.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogMetadataDto(
    @SerialName("latency_ms")
    val latencyMs: Int? = null,
    @SerialName("is_ai_generated")
    val isAiGenerated: Boolean? = null
)
