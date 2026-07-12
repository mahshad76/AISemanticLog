package com.example.home.domain.model

data class LogEntry(
    var id: String,
    var severity: SeverityGroup,
    var tag: String,
    var message: String,
    var metadata: LogMetadata
)

enum class SeverityGroup(val label: String) {
    DEBUG("Debug"),
    INFO("Info"),
    WARN("Warn"),
    ERROR("Error"),
    FATAL("Fatal"),
    UNKNOWN("Unknown");

    companion object {
        fun from(value: String?): SeverityGroup {
            val normalizedValue = value
                ?.trim()
                ?.uppercase()
                ?: return UNKNOWN

            return entries.firstOrNull { severity ->
                severity.label.uppercase() == normalizedValue
            } ?: UNKNOWN
        }
    }
}