package com.example.home.data.mapper

import com.example.home.domain.model.Log
import com.example.home.domain.model.LogEntry
import com.example.home.domain.model.LogMetadata
import com.example.home.domain.model.LogMetadata.Companion.DEFAULT
import com.example.home.domain.model.SeverityGroup
import com.example.model.LogEntryDto
import com.example.model.LogMetadataDto
import com.example.model.LogsResponseDto

fun LogsResponseDto.toLog(): Log =
    Log(
        totalCount = this.totalCount ?: 0,
        sessionId = this.sessionId ?: "",
        data = this.data?.map { it.toLogEntry() } ?: emptyList()
    )

fun LogEntryDto.toLogEntry(): LogEntry = LogEntry(
    id = this.id ?: "",
    timestamp = this.timestamp ?: "",
    severity = SeverityGroup.from(this.severity),
    tag = this.tag ?: "",
    message = this.message ?: "",
    metadata = this.metadata?.toLogMetaData() ?: DEFAULT
)

fun LogMetadataDto.toLogMetaData(): LogMetadata = LogMetadata(
    latencyMs = this.latencyMs ?: 0,
    isAiGenerated = this.isAiGenerated ?: false
)