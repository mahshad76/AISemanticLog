package com.example.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogEntryDto(
    @SerialName("id")
    var id: String? = null,
    @SerialName("timestamp")
    var timestamp: String? = null,
    @SerialName("severity")
    var severity: String? = null,
    @SerialName("tag")
    var tag: String? = null,
    @SerialName("message")
    var message: String? = null,
    @SerialName("metadata")
    var metadata: LogMetadataDto? = null
)
