package com.example.home.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.home.domain.model.LogEntry

@Composable
internal fun LogsList(
    logs: List<LogEntry>,
    navigateToDetail: (LogEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 300.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(
            key = { item -> item.id },
            items = logs
        ) { log ->
            LogItem(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                log = log,
                navigateToDetail = navigateToDetail
            )
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
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier
            .clickable(onClick = { navigateToDetail(log) })
            .clip(MaterialTheme.shapes.small)
            .height(IntrinsicSize.Min),
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = log.tag, style = MaterialTheme.typography.titleMedium)
            Text(text = log.message)
            Text(log.severity.label)
        }
    }
}