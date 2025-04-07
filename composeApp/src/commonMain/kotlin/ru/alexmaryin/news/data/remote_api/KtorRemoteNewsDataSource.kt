package ru.alexmaryin.news.data.remote_api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import ru.alexmaryin.core.data.safeCall
import ru.alexmaryin.core.domain.DataError
import ru.alexmaryin.core.domain.Result
import ru.alexmaryin.news.data.dto_models.SpaceNewsResponseDTO
import ru.alexmaryin.news.data.remote_api.RemoteNewsDataSource.Companion.SEARCH_URL

class KtorRemoteNewsDataSource(
    private val httpClient: HttpClient
) : RemoteNewsDataSource {
    override suspend fun searchNews(
        query: String,
        limit: Int
    ): Result<SpaceNewsResponseDTO, DataError.Remote> {
        return safeCall {
            httpClient.get(urlString = SEARCH_URL) {
                parameter("limit", limit)
                parameter("search", query)
            }
        }
    }
}