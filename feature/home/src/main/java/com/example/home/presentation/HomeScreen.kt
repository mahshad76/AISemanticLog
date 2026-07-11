package com.example.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.material3.Text
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun HomeScreen(viewModel: HomeViewModel, navigateToDetail: () -> Unit) {
    val homeState by viewModel.homeUiState.collectAsStateWithLifecycle()
    Column() {
        //Text(homeState)
    }
}