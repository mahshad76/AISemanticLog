package com.example.home.domain.model

import kotlinx.serialization.SerialName

data class LogMetadata(
    val latencyMs: Int,
    val isAiGenerated: Boolean
) {
    companion object {
        val DEFAULT = LogMetadata(
            latencyMs = 0,
            isAiGenerated = false
        )
    }
}
