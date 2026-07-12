package com.example.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.detail.navigation.DetailRoute
import com.example.detail.navigation.DetailUiModel

@Composable
fun DetailScreen(uiModel: DetailUiModel, onBackClick: () -> Unit) {
    Text(uiModel.metadata)
}