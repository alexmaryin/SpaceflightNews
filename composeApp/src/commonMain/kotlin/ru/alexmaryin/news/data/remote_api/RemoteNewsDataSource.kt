package ru.alexmaryin.news.data.remote_api

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.alexmaryin.news.domain.models.Article

interface RemoteNewsDataSource {
    fun searchNews(
        query: String,
        limit: Int = DEFAULT_LIMIT
    ): Flow<PagingData<Article>>

    companion object {
        const val BASE_URL = "https://api.spaceflightnewsapi.net/v4"
        const val SEARCH_URL = "$BASE_URL/articles/"
        const val DEFAULT_LIMIT = 10
    }
}