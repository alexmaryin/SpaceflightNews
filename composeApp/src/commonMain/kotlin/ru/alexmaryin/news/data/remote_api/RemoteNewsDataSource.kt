package ru.alexmaryin.news.data.remote_api

import ru.alexmaryin.core.domain.DataError
import ru.alexmaryin.core.domain.Result
import ru.alexmaryin.news.data.dto_models.SpaceNewsResponseDTO

interface RemoteNewsDataSource {
    suspend fun searchNews(
        query: String,
        limit: Int = 10
    ): Result<SpaceNewsResponseDTO, DataError.Remote>

    companion object {
        const val BASE_URL = "https://api.spaceflightnewsapi.net/v4"
        const val SEARCH_URL = "$BASE_URL/articles/"
    }
}