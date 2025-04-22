package ru.alexmaryin.app

import kotlinx.serialization.Serializable

sealed interface Navigation {
    @Serializable data object NewsGraph : Navigation
    @Serializable data object NewsScreen : Navigation
    @Serializable data object AboutScreen : Navigation
    @Serializable data class ArticleDetails(val articleId: Int) : Navigation
}