package ru.alexmaryin.news.data.remote_api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import ru.alexmaryin.news.data.dto_models.SpaceNewsResponseDTO
import ru.alexmaryin.news.data.mappers.toArticle
import ru.alexmaryin.news.data.remote_api.RemoteNewsDataSource.Companion.SEARCH_URL
import ru.alexmaryin.news.domain.models.Article

class KtorPagingSource(
    private val httpClient: HttpClient,
    private val searchQuery: String
) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchor ->
            val page = state.closestPageToPosition(anchor)
            page?.prevKey?.plus(state.config.pageSize)
                ?: page?.nextKey?.minus(state.config.pageSize)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val offset = params.key?.coerceAtLeast(0) ?: 0
        val limit = params.loadSize

        return try {
            val response = httpClient.get(urlString = SEARCH_URL) {
                if (searchQuery.isNotBlank()) parameter("search", searchQuery)
                parameter("offset", offset)
                parameter("limit", limit)
            }.body<SpaceNewsResponseDTO>()

            LoadResult.Page(
                data = response.results.map { it.toArticle() },
                prevKey = if (offset == 0) null else offset - limit,
                nextKey = if (response.next != null) offset + limit else null
            )
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}