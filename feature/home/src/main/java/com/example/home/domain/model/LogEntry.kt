package com.example.home.domain.model

import com.example.model.LogMetadataDto
import kotlinx.serialization.SerialName

data class LogEntry(
    var id: String,
    var timestamp: String,
    var severity: String,
    var tag: String,
    var message: String,
    var metadata: LogMetadata
)
