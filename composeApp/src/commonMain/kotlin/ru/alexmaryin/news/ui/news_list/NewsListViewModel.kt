package ru.alexmaryin.news.ui.news_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.alexmaryin.core.domain.onError
import ru.alexmaryin.core.domain.onSuccess
import ru.alexmaryin.core.ui.toUiText
import ru.alexmaryin.news.domain.SpaceNewsRepository
import ru.alexmaryin.news.domain.models.Article

class NewsListViewModel(
    private val repository: SpaceNewsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NewsListState())
    val state = _state
        .onStart {
            if (cachedArticles.isEmpty()) observeSearchQuery()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value)

    private val cachedArticles = emptyList<Article>()
    private var searchJob: Job? = null

    fun onAction(action: NewsListAction) {
        when (action) {
            is NewsListAction.OnNewsItemClick -> TODO()
            is NewsListAction.OnSearchQueryChange -> _state.update {
                it.copy(searchQuery = action.query)
            }

            is NewsListAction.OnTabSelected -> _state.update {
                it.copy(selectedTabIndex = action.index)
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state.map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> _state.update {
                        it.copy(
                            error = null,
                            searchResult = cachedArticles
                        )
                    }

                    query.length > 5 -> {
                        searchJob?.cancel()
                        searchJob = searchNews(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchNews(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(isLoading = true)
        }
        repository.searchNews(query)
            .onSuccess { results ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = null,
                        searchResult = results
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        searchResult = emptyList(),
                        error = error.toUiText()
                    )
                }
            }
    }
}