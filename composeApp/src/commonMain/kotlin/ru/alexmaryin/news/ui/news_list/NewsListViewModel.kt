package ru.alexmaryin.news.ui.news_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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

            is NewsListAction.OnRefresh -> _state.update {
                it.copy(refresh = true)
            }

            is NewsListAction.OnRefreshed -> _state.update {
                it.copy(refresh = false)
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
            it.copy(
                articlesFlow = repository.searchNews(query).cachedIn(viewModelScope),
                scrollState = ScrollState.SCROLL_TO_START
            )
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