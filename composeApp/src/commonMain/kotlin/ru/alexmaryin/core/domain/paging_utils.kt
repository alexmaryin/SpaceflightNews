package ru.alexmaryin.core.domain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.network.sockets.*
import io.ktor.util.network.*

@DslMarker
annotation class PagingDSL

@PagingDSL
class PagingHandlerScope<T : Any>(
    private val items: LazyPagingItems<T>
) {
    private var handled = false

    @Composable
    fun onEmpty(body: @Composable () -> Unit) {
        if (handled) return
        if (items.loadState.refresh !is LoadState.Error && items.itemCount == 0) {
            handled = true
            body()
        }
    }

    @Composable
    fun onRefresh(body: @Composable () -> Unit) {
        if (handled) return
        if (items.loadState.refresh is LoadState.Loading) {
            handled = true
            body()
        }
    }

    @Composable
    fun onSuccess(body: @Composable (LazyPagingItems<T>) -> Unit) {
        if (!handled) {
            handled = true
            body(items)
        }
    }

    @Composable
    fun onError(body: @Composable (DataError) -> Unit) {
        if (handled) return
        if (items.loadState.refresh is LoadState.Error) {
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
            handled = true
            body(result)
        } else this
    }
}

@Composable
fun <T : Any> HandlePagingItems(
    items: LazyPagingItems<T>,
    content: @Composable PagingHandlerScope<T>.() -> Unit
) {
    remember(items.loadState) {
        derivedStateOf { items.loadState }
    }.value

    PagingHandlerScope(items).content()
}