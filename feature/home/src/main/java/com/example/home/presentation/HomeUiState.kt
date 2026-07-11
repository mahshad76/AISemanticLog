package com.example.home.presentation

import androidx.compose.runtime.Immutable
import com.example.home.domain.model.LogEntry

internal sealed interface HomeUiState {

    /**
     * Represents the success state, where the log is available.
     */
    @Immutable
    data class Success(val logs: List<LogEntry>) : HomeUiState

    /**
     * Represents the error state, where an error occurred while fetching the data.
     */
    data class Error(val errorMessage: String?) : HomeUiState

    /**
     * Represents the idle state, where there is no data to display.
     */
    data object Idle : HomeUiState

    /**
     * Represents the loading state, where the data is being fetched.
     */
    data object Loading : HomeUiState
}