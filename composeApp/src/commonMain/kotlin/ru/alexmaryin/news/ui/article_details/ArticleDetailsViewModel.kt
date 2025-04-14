package ru.alexmaryin.news.ui.article_details

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ArticleDetailsViewModel : ViewModel() {
    private val _state = MutableStateFlow(ArticleDetailsState())
    val state = _state.asStateFlow()

    fun onAction(action: ArticleDetailsAction) {
        when (action) {
            is ArticleDetailsAction.onSelectedArticleChange -> {
                _state.update {
                    it.copy(article = action.article)
                }
            }
            ArticleDetailsAction.onFavouriteClick -> {
                TODO()
            }
            else -> Unit
        }
    }
}