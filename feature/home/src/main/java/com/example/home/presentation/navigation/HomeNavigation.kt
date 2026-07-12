package com.example.home.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.home.domain.model.LogEntry
import com.example.home.presentation.HomeScreen
import com.example.home.presentation.HomeViewModel
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeScreen(navigateToDetail: (LogEntry) -> Unit) {
    composable<HomeRoute> { backStackEntry: NavBackStackEntry ->
        val viewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
        HomeScreen(viewModel = viewModel, navigateToDetail = navigateToDetail)
    }
}