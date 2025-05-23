package ru.alexmaryin.news.data.repository

import androidx.sqlite.SQLiteException
import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.alexmaryin.core.domain.DataError
import ru.alexmaryin.core.domain.EmptyResult
import ru.alexmaryin.core.domain.Result
import ru.alexmaryin.news.data.local_api.database.ArticlesDAO
import ru.alexmaryin.news.data.mappers.toArticle
import ru.alexmaryin.news.data.mappers.toEntity
import ru.alexmaryin.news.data.remote_api.RemoteNewsDataSource
import ru.alexmaryin.news.domain.SpaceNewsRepository
import ru.alexmaryin.news.domain.models.Article

class DefaultSpaceNewsRepository(
    private val remoteDataSource: RemoteNewsDataSource,
    private val localDataSource: ArticlesDAO
) : SpaceNewsRepository {
    override fun searchNews(query: String): Flow<PagingData<Article>> {
        return remoteDataSource.searchNews(query)
    }

    override fun getFavouriteArticles(): Flow<List<Article>> =
        localDataSource.getFavouriteArticles().map { entities ->
            entities.map { it.toArticle() }
        }

    override suspend fun markAsFavourite(article: Article): EmptyResult<DataError.Local> {
        return try {
            localDataSource.insertFavouriteArticle(
                article = article.toEntity(),
                authors = article.authors.map { it.toEntity(article.id) },
                launches = article.launches.map { it.toEntity(article.id) },
                events = article.events.map { it.toEntity(article.id) }
            )
            Result.Success(Unit)
        } catch (_: SQLiteException) {
            Result.Error(DataError.Local.DISK_ERROR)
        }
    }

    override suspend fun deleteFromFavourites(id: Int) {
        localDataSource.deleteFromFavourites(id)
    }
}