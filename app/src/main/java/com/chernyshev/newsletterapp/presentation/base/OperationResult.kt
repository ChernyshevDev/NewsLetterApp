package com.chernyshev.newsletterapp.presentation.base

sealed class OperationResult<out T> {
    data class ResultSuccess<T>(val result: T) : OperationResult<T>()
    data class ResultError(val error: String?) : OperationResult<Nothing>()

    suspend fun onSuccess(block: suspend (T) -> Unit): OperationResult<T> {
        if (this is ResultSuccess) block(result)
        return this
    }

    fun onError(block: (String?) -> Unit): OperationResult<T> {
        if (this is ResultError) block(error)
        return this
    }
}