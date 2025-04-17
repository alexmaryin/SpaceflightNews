package ru.alexmaryin.news.data.remote_api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import app.cash.paging.PagingData
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import ru.alexmaryin.news.domain.models.Article

class KtorRemoteNewsDataSource(
    private val httpClient: HttpClient
) : RemoteNewsDataSource {
    override fun searchNews(
        query: String,
        limit: Int
    ): Flow<PagingData<Article>> {
        val pager = Pager<Int, Article>(
            config = PagingConfig(
                pageSize = RemoteNewsDataSource.DEFAULT_LIMIT,
                initialLoadSize = RemoteNewsDataSource.DEFAULT_LIMIT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPagingSource(httpClient, query) }
        )

        return pager.flow
    }
}