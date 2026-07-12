package com.example.home.presentation

/**
 * Sealed interface representing events related to home screen
 */
internal sealed interface HomeEvent {
    /**
     * Represents an event to fetch the list of logs
     */
    data object GetLogs : HomeEvent

    /**
     * Represents an event to consume and clear any existing error messages
     */
    data object ConsumeErrorMessage : HomeEvent

    /**
     * Represents an event to search for log based on a given query
     */
    data class Search(val query: String) : HomeEvent
}