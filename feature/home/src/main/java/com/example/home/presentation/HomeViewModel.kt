package com.example.home.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.stringresolver.StringResolver
import com.example.home.R
import com.example.home.domain.model.LogEntry
import com.example.home.domain.usecase.DefaultFetchLogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val fetchLogUseCase: DefaultFetchLogUseCase,
    private val stringResolver: StringResolver
) : ViewModel() {
    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val homeUiState = _homeUiState.asStateFlow()
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _isSearchVisible = MutableStateFlow(false)
    val isSearchVisible = _isSearchVisible.asStateFlow()

    val filteredLogs = combine(homeUiState, searchQuery) { uiState, query ->
        if (uiState is HomeUiState.Success) {
            filterLogs(uiState.logs, query)
        } else {
            emptyList<LogEntry>()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    private fun filterLogs(
        logs: List<LogEntry>,
        query: String
    ): List<LogEntry> {
        val normalizedQuery = query.trim()

        if (normalizedQuery.isBlank()) return logs

        return logs.filter { log ->
            listOf(
                log.id,
                log.timestamp,
                log.severity.label,
                log.tag,
                log.message
            ).any { value ->
                value.contains(normalizedQuery, ignoreCase = true)
            }
        }
    }

    fun clearSearchQuery() {
        _searchQuery.value = ""
    }

    fun toggleSearchVisibility() {
        _isSearchVisible.value = !_isSearchVisible.value
    }

    private fun getLogs() {
        viewModelScope.launch {
            if (_homeUiState.value !is HomeUiState.Success) {

                _homeUiState.value = HomeUiState.Loading

                val result = fetchLogUseCase()
                when {
                    result.isSuccess -> {
                        setHomeUiStateToSuccess(logs = result.getOrNull()?.data)
                    }

                    result.isFailure -> {
                        setHomeUiStateToError(
                            result.exceptionOrNull()?.message ?: stringResolver.findString(
                                R.string.error_general
                            )
                        )
                    }
                }
            }
        }
    }

    @VisibleForTesting
    fun setHomeUiStateToSuccess(
        logs: List<LogEntry>?
    ) {
        _homeUiState.update {
            HomeUiState.Success(logs = logs ?: emptyList())
        }
    }

    @VisibleForTesting
    fun setHomeUiStateToError(message: String) {
        _homeUiState.update {
            HomeUiState.Error(errorMessage = message)
        }
    }

    private fun consumeErrorMessage() {
        _homeUiState.update {
            HomeUiState.Error(null)
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.ConsumeErrorMessage -> {
                consumeErrorMessage()
            }

            is HomeEvent.GetLogs -> {
                getLogs()
            }

            is HomeEvent.Search -> {
                _searchQuery.value = event.query
            }
        }
    }
}