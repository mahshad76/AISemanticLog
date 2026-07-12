package com.example.home.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.stringresolver.StringResolver
import com.example.home.R
import com.example.home.domain.model.LogEntry
import com.example.home.domain.model.LogGroup
import com.example.home.domain.model.SeverityGroup
import com.example.home.domain.usecase.DefaultFetchLogUseCase
import com.example.threading.qualifiers.DefaultDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val fetchLogUseCase: DefaultFetchLogUseCase,
    private val stringResolver: StringResolver,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val homeUiState = _homeUiState.asStateFlow()

    private val _isSearchVisible = MutableStateFlow(false)
    val isSearchVisible = _isSearchVisible.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    @OptIn(FlowPreview::class)
    private val debouncedSearchQuery = searchQuery
        .map { query -> query.trim() }
        .distinctUntilChanged()
        .debounce { query ->
            if (query.isBlank()) 0L else 300L
        }

    val filteredLogs = combine(homeUiState, debouncedSearchQuery) { uiState, query ->
        if (uiState is HomeUiState.Success) {
            filterLogs(uiState.logs, query).let {
                it.map { group ->
                    val severityPercentages = group.entries.analyzeSeverityPercentage()
                    group.copy(severityPercentages = severityPercentages)
                }
            }
        } else {
            emptyList<LogGroup>()
        }
    }
        .flowOn(defaultDispatcher)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private fun filterLogs(
        logs: List<LogGroup>,
        query: String
    ): List<LogGroup> {
        if (query.isBlank()) return logs
        return logs.mapNotNull { group ->
            val filteredEntries = group.entries
                .filter { entry ->
                    entry.matchesQuery(query)
                }
            // Remove group if no match found
            group
                .takeIf { filteredEntries.isNotEmpty() }
                ?.copy(entries = filteredEntries)
        }
    }

    private fun List<LogEntry>.analyzeSeverityPercentage(): Map<SeverityGroup, Float> {
        if (this.isEmpty()) return emptyMap()
        val totalCount = this.size.toFloat()
        return this.groupBy { it.severity }
            .mapValues { (_, entriesOfSeverity) ->
                entriesOfSeverity.size / totalCount
            }
    }

    private fun LogEntry.matchesQuery(query: String): Boolean =
        severity.label.contains(query, ignoreCase = true) ||
                tag.contains(query, ignoreCase = true) ||
                message.contains(query, ignoreCase = true)

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
        logs: List<LogGroup>?
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