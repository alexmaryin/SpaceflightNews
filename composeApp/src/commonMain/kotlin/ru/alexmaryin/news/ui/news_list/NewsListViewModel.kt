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

class NewsListViewModel(
    private val repository: SpaceNewsRepository
) : ViewModel() {


    private var searchJob: Job? = null
    private var favouritesJob: Job? = null

    private val _state = MutableStateFlow(NewsListState())
    val state = _state
        .onStart {
            observeSearchQuery()
            observeFavouritesNews()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value)

    fun onAction(action: NewsListAction) {
        when (action) {
            is NewsListAction.OnSearchQueryChange -> _state.update {
                it.copy(searchQuery = action.query)
            }

            is NewsListAction.OnTabSelected -> _state.update {
                it.copy(selectedTabIndex = action.index)
            }

            is NewsListAction.OnScrollToStart -> _state.update {
                it.copy(scrollState = ScrollState.SCROLL_TO_START)
            }

            is NewsListAction.OnScrolledUp -> _state.update {
                it.copy(scrollState = ScrollState.SCROLLED_UP)
            }

            is NewsListAction.OnScrollDown -> _state.update {
                it.copy(scrollState = ScrollState.SCROLLED_DOWN)
            }

            is NewsListAction.OnRefresh -> {
                searchJob?.cancel()
                searchJob = searchNews(state.value.searchQuery)
            }

            else -> Unit
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state.map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                searchJob?.cancel()
                searchJob = searchNews(query)
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

    private fun observeFavouritesNews() {
        favouritesJob?.cancel()
        favouritesJob = repository.getFavouriteArticles()
            .onEach { favouriteArticles ->
                _state.update { it.copy(favouriteArticles = favouriteArticles) }
            }.launchIn(viewModelScope)
    }
}