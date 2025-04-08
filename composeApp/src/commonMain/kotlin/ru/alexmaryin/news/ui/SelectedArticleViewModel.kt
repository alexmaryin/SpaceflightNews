package ru.alexmaryin.news.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.alexmaryin.news.domain.models.Article

class SelectedArticleViewModel : ViewModel() {
    private val _selectedArticle = MutableStateFlow<Article?>(null)
    val selectedArticle = _selectedArticle.asStateFlow()

    fun onSelectArticle(article: Article?) {
        _selectedArticle.value = article
    }
}