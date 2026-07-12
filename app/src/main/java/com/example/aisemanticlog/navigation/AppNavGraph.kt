package com.example.aisemanticlog.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.detail.navigation.DetailRoute
import com.example.detail.navigation.DetailUiModel
import com.example.detail.navigation.detailScreen
import com.example.detail.navigation.navigateToDetails
import com.example.home.presentation.navigation.HomeRoute
import com.example.home.presentation.navigation.homeScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier
    ) {
        homeScreen(navigateToDetail = { logEntry ->
            navController.navigateToDetails(
                DetailUiModel(
                    id = logEntry.id,
                    timestamp = logEntry.timestamp,
                    severity = logEntry.severity.label,
                    tag = logEntry.tag,
                    message = logEntry.message,
                    metadata = logEntry.metadata.toString()
                )
            )
        })
        detailScreen { navController.navigateUp() }
    }
}