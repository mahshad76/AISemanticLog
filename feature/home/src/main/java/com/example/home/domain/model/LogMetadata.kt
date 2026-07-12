package com.example.home.domain.model

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
