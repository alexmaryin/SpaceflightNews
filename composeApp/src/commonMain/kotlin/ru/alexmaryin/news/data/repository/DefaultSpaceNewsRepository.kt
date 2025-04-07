package ru.alexmaryin.news.data.repository

import ru.alexmaryin.core.domain.DataError
import ru.alexmaryin.core.domain.Result
import ru.alexmaryin.core.domain.map
import ru.alexmaryin.news.data.mappers.toArticle
import ru.alexmaryin.news.data.remote_api.RemoteNewsDataSource
import ru.alexmaryin.news.domain.SpaceNewsRepository
import ru.alexmaryin.news.domain.models.Article

class DefaultSpaceNewsRepository(
    private val remoteDataSource: RemoteNewsDataSource,
) : SpaceNewsRepository {
    override suspend fun searchNews(query: String): Result<List<Article>, DataError.Remote> {
        return remoteDataSource.searchNews(query).map {
            it.results.map { dto ->
                dto.toArticle()
            }
        }
    }
}