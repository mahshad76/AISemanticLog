package com.example.home.domain.model

data class LogGroup(
    val timestamp: String,
    val entries: List<LogEntry>
)
