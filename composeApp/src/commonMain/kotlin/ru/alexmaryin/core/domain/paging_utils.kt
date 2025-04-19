package ru.alexmaryin.core.domain

import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import io.ktor.client.call.DoubleReceiveException
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.plugins.ResponseException
import io.ktor.network.sockets.SocketTimeoutException
import io.ktor.util.network.UnresolvedAddressException

data class PageHandleable <T : Any>(
    val items: LazyPagingItems<T>,
    val isHandled: Boolean = false
) {
    fun markHandled() = PageHandleable(items, true)
}

@Composable
fun <T : Any> LazyPagingItems<T>.asHandleable(): PageHandleable<T> = PageHandleable(this)

@Composable
inline fun <T : Any> PageHandleable<T>.onEmpty(crossinline body: @Composable () -> Unit): PageHandleable<T> {
    return if (!isHandled && items.loadState.refresh !is LoadState.Error && items.itemCount == 0) {
        body()
        markHandled()
    } else this
}

@Composable
inline fun <T : Any> PageHandleable<T>.onRefresh(crossinline body: @Composable () -> Unit): PageHandleable<T> {
    return if (!isHandled && items.loadState.refresh is LoadState.Loading) {
        body()
        markHandled()
    } else this
}

@Composable
inline fun <T : Any> PageHandleable<T>.onError(crossinline body: @Composable (DataError) -> Unit): PageHandleable<T> {
    return if (!isHandled && items.loadState.refresh is LoadState.Error) {
        val error = (items.loadState.refresh as LoadState.Error).error
        val result = when (error) {
            is NoTransformationFoundException -> DataError.Remote.SERIALIZATION_ERROR
            is DoubleReceiveException -> DataError.Remote.SERIALIZATION_ERROR
            is SocketTimeoutException -> DataError.Remote.REQUEST_TIMEOUT
            is UnresolvedAddressException -> DataError.Remote.NO_INTERNET_CONNECTION
            is ResponseException -> when (error.response.status.value) {
                408 -> DataError.Remote.REQUEST_TIMEOUT
                429 -> DataError.Remote.TOO_MANY_REQUESTS
                in 500..599 -> DataError.Remote.SERVER_ERROR
                else -> DataError.Remote.UNKNOWN_ERROR
            }
            else -> DataError.Remote.UNKNOWN_ERROR
        }
        body(result)
        markHandled()
    } else this
}

@Composable
inline fun <T : Any> PageHandleable<T>.onSuccess(crossinline body: @Composable (LazyPagingItems<T>) -> Unit): PageHandleable<T> {
    return if (!isHandled) {
        body(items)
        markHandled()
    } else this
}

