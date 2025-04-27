package ru.alexmaryin.news.data.remote_api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import ru.alexmaryin.news.domain.models.Article

class KtorRemoteNewsDataSource : RemoteNewsDataSource, KoinComponent {
    override fun searchNews(
        query: String,
        limit: Int
    ): Flow<PagingData<Article>> {
        val pager = Pager(
            config = PagingConfig(
                pageSize = RemoteNewsDataSource.DEFAULT_LIMIT,
                initialLoadSize = RemoteNewsDataSource.DEFAULT_LIMIT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                get<PagingSource<Int, Article>> { parametersOf(query) }
            }
        )
        return pager.flow
    }
}