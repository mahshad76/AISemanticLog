package com.example.home.data.mapper

import com.example.home.domain.model.Log
import com.example.home.domain.model.LogEntry
import com.example.home.domain.model.LogGroup
import com.example.home.domain.model.LogMetadata
import com.example.home.domain.model.LogMetadata.Companion.DEFAULT
import com.example.home.domain.model.SeverityGroup
import com.example.model.LogEntryDto
import com.example.model.LogMetadataDto
import com.example.model.LogsResponseDto
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun LogsResponseDto.toLog(): Log {
    val inputParser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    val hourFormatter = SimpleDateFormat("h a", Locale.US).apply {
        timeZone = TimeZone.getDefault()
    }

    return Log(
        totalCount = this.totalCount ?: 0,
        sessionId = this.sessionId ?: "",
        data = this.data?.groupBy { logEntry ->
            logEntry.timestamp?.let { ts ->
                try {
                    val safeTs = if (ts.length > 24) ts.substring(0, 23) + "Z" else ts

                    val date = inputParser.parse(safeTs)
                    if (date != null) hourFormatter.format(date) else "Unknown Time"
                } catch (e: Exception) {
                    "Unknown Time"
                }
            } ?: "Unknown Time"
        }
            ?.map { (hourKey, entries) ->
                LogGroup(
                    timestamp = hourKey,
                    entries = entries.map { it.toLogEntry() }
                )
            }
            ?: emptyList()
    )
}

fun LogEntryDto.toLogEntry(): LogEntry = LogEntry(
    id = this.id ?: "",
    severity = SeverityGroup.from(this.severity),
    tag = this.tag ?: "",
    message = this.message ?: "",
    metadata = this.metadata?.toLogMetaData() ?: DEFAULT
)

fun LogMetadataDto.toLogMetaData(): LogMetadata = LogMetadata(
    latencyMs = this.latencyMs ?: 0,
    isAiGenerated = this.isAiGenerated ?: false
)