package com.example.common.error

sealed class RepositoryError : Exception() {
    data class NetworkError(val code: Int, override val message: String? = null) : RepositoryError()
    data class NoDataError(override val message: String? = null) : RepositoryError()
    data class UnknownError(override val cause: Throwable? = null) : RepositoryError()
}