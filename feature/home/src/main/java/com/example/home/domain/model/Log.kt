package com.example.home.domain.model

data class Log(
    val totalCount: Int,
    val sessionId: String,
    val data: List<LogGroup>
) {
    companion object {
        val DEFAULT = Log(
            totalCount = 100,
            sessionId = "300",
            data = emptyList()
        )
    }
}
