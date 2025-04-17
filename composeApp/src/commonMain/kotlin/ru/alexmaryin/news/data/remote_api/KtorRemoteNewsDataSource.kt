package ru.alexmaryin.news.data.remote_api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform
import ru.alexmaryin.news.domain.models.Article

class KtorRemoteNewsDataSource : RemoteNewsDataSource {
    override fun searchNews(
        query: String,
        limit: Int
    ): Flow<PagingData<Article>> {
        val pagingSource = KoinPlatform.getKoin().get<NewsPagingSource> { parametersOf(query) }
        val pager = Pager<Int, Article>(
            config = PagingConfig(
                pageSize = RemoteNewsDataSource.DEFAULT_LIMIT,
                initialLoadSize = RemoteNewsDataSource.DEFAULT_LIMIT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { pagingSource }
        )

        return pager.flow
    }
}