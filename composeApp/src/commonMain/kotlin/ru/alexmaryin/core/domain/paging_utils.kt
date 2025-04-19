package ru.alexmaryin.core.domain

import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import io.ktor.client.call.DoubleReceiveException
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.plugins.ResponseException
import io.ktor.network.sockets.SocketTimeoutException
import io.ktor.util.network.UnresolvedAddressException

@Composable
inline fun <T : Any> LazyPagingItems<T>.onEmpty(body: @Composable () -> Unit): LazyPagingItems<T>? {
    return if (loadState.refresh !is LoadState.Error && itemCount == 0) {
        body()
        null
    } else this
}

@Composable
inline fun <T : Any> LazyPagingItems<T>.onRefresh(body: @Composable () -> Unit): LazyPagingItems<T>? {
    return if (loadState.refresh is LoadState.Loading) {
        body()
        null
    } else this
}

@Composable
inline fun <T : Any> LazyPagingItems<T>.onError(body: @Composable (DataError) -> Unit): LazyPagingItems<T>? {
    return if (loadState.refresh is LoadState.Error) {
        val error = when (val e = (loadState.refresh as LoadState.Error).error) {
            is NoTransformationFoundException -> DataError.Remote.SERIALIZATION_ERROR
            is DoubleReceiveException -> DataError.Remote.SERIALIZATION_ERROR
            is SocketTimeoutException -> DataError.Remote.REQUEST_TIMEOUT
            is UnresolvedAddressException -> DataError.Remote.NO_INTERNET_CONNECTION
            is ResponseException -> when (e.response.status.value) {
                408 -> DataError.Remote.REQUEST_TIMEOUT
                429 -> DataError.Remote.TOO_MANY_REQUESTS
                in 500..599 -> DataError.Remote.SERVER_ERROR
                else -> DataError.Remote.UNKNOWN_ERROR
            }
            else -> DataError.Remote.UNKNOWN_ERROR
        }
        body(error)
        null
    } else this
}

