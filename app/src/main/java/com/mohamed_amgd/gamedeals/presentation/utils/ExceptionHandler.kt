package com.mohamed_amgd.gamedeals.presentation.utils

import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.HttpException
import java.io.IOException

fun exceptionHandler(action: (String) -> Unit) = CoroutineExceptionHandler { _, throwable ->
    val errorMessage = when (throwable) {
        is IOException -> "Check your internet connection"
        is HttpException ->
            "Service unavailable at the moment \nPlease try again later"
        else -> "${throwable.message}"
    }
    action(errorMessage)
}
