package ru.alexmaryin.news.domain

import ru.alexmaryin.core.domain.DataError
import ru.alexmaryin.core.domain.Result
import ru.alexmaryin.news.domain.models.Article

interface SpaceNewsRepository {
    suspend fun searchNews(query: String): Result<List<Article>, DataError.Remote>
}