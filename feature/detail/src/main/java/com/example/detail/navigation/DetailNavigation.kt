package com.example.detail.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.detail.DetailScreen
import com.example.detail.navigation.CustomNavType.detailUiModelNavType
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlin.reflect.typeOf
import kotlinx.serialization.json.Json

@Serializable
data class DetailRoute(
    val uiModel: DetailUiModel
)

@Serializable
data class DetailUiModel(
    var id: String,
    var severity: String,
    var tag: String,
    var message: String,
    var metadata: String
)

object CustomNavType {
    val detailUiModelNavType = object : NavType<DetailUiModel>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): DetailUiModel? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): DetailUiModel {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: DetailUiModel): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: DetailUiModel) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}

fun NavGraphBuilder.detailScreen(onBackClick: () -> Unit) {
    composable<DetailRoute>(
        typeMap = mapOf(
            typeOf<DetailUiModel>() to detailUiModelNavType,
        )
    ) { backStackEntry ->
        val detailRoute = backStackEntry.toRoute<DetailRoute>()
        DetailScreen(uiModel = detailRoute.uiModel, onBackClick = onBackClick)
    }
}

fun NavController.navigateToDetails(uiModel: DetailUiModel) =
    navigate(route = DetailRoute(uiModel))