package ru.alexmaryin.news.ui.article_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.alexmaryin.app.Navigation
import ru.alexmaryin.news.domain.SpaceNewsRepository

class ArticleDetailsViewModel(
    private val repository: SpaceNewsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(ArticleDetailsState())
    val state = _state.onStart {
        observeFavouriteStatus()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value)

    private val articleId = savedStateHandle.toRoute<Navigation.ArticleDetails>().articleId

    fun onAction(action: ArticleDetailsAction) {
        when (action) {
            is ArticleDetailsAction.onSelectedArticleChange -> {
                _state.update {
                    it.copy(article = action.article)
                }
            }
            ArticleDetailsAction.onFavouriteClick -> {
                viewModelScope.launch {
                    if (state.value.isFavourite) {
                        repository.deleteFromFavourites(articleId)
                    } else {
                        state.value.article?.let {
                            repository.markAsFavourite(it)
                        }
                    }
                }
            }
            else -> Unit
        }
    }

    private fun observeFavouriteStatus() {
        repository.isArticleFavourite(articleId)
            .onEach { favourite ->
                _state.update { it.copy(isFavourite = favourite) }
            }.launchIn(viewModelScope)
    }
}