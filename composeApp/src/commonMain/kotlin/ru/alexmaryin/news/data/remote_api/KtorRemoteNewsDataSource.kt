package ru.alexmaryin.news.data.remote_api

import app.cash.paging.Pager
import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.alexmaryin.news.domain.models.Article

class KtorRemoteNewsDataSource(
    private val pager: Pager<Int, Article>
) : RemoteNewsDataSource {
    override fun searchNews(
        query: String,
        limit: Int
    ): Flow<PagingData<Article>> = pager.flow
}