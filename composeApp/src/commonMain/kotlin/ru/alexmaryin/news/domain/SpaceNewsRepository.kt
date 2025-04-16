package ru.alexmaryin.news.domain

import kotlinx.coroutines.flow.Flow
import ru.alexmaryin.core.domain.DataError
import ru.alexmaryin.core.domain.EmptyResult
import ru.alexmaryin.core.domain.Result
import ru.alexmaryin.news.domain.models.Article

interface SpaceNewsRepository {
    suspend fun searchNews(query: String): Result<List<Article>, DataError.Remote>
    fun getFavouriteArticles(): Flow<List<Article>>
    fun isArticleFavourite(id: Int): Flow<Boolean>
    suspend fun markAsFavourite(article: Article): EmptyResult<DataError.Local>
    suspend fun deleteFromFavourites(id: Int)
}