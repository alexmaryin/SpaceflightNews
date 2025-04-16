package ru.alexmaryin.news.ui.article_details

import ru.alexmaryin.news.domain.models.Article

sealed interface ArticleDetailsAction {
    data object onBackClick: ArticleDetailsAction
    data object onFavouriteClick : ArticleDetailsAction
    data class onSelectedArticleChange(val article: Article): ArticleDetailsAction
}