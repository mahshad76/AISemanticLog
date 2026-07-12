package com.example.home.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.home.domain.model.LogEntry
import com.example.home.domain.model.LogGroup
import com.example.home.domain.model.SeverityGroup

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun LogsList(
    logs: List<LogGroup>,
    navigateToDetail: (LogEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        logs.forEach { group ->
            stickyHeader {
                TimestampHeader(
                    timestamp = group.timestamp, modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    severityPercentages = group.severityPercentages
                )
            }

            items(
                items = group.entries,
                key = { entry -> entry.id }
            ) { entry ->
                LogItem(
                    log = entry,
                    navigateToDetail = navigateToDetail,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun TimestampHeader(
    timestamp: String,
    severityPercentages: Map<SeverityGroup, Float>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = timestamp,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 10.dp
                )
            )
            SeverityIndicator(percentages = severityPercentages)
        }
    }
}

@Composable
private fun LogItem(
    log: LogEntry,
    navigateToDetail: (LogEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { navigateToDetail(log) },
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = log.tag,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = log.message,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = log.severity.label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}