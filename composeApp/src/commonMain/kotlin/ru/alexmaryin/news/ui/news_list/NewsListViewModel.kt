package ru.alexmaryin.news.ui.news_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
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
    val state = _state.onStart {
        observeSearchQuery()
        observeFavouritesNews()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value)

    fun onAction(action: NewsListAction) {
        when (action) {
            is NewsListAction.OnSearchQueryChange -> _state.update {
                it.copy(searchQuery = action.query)
            }

            is NewsListAction.OnTabSelected -> _state.update {
                it.copy(selectedTabIndex = action.index)
            }

            is NewsListAction.OnBarButtonClicked -> {
                if (state.value.isAtTop) _state.update {
                    it.copy(isRefreshing = true)
                } else _state.update {
                    it.copy(scrollEvent = ScrollEvent.SCROLL_UP)
                }
            }

            is NewsListAction.OnScrollHandled -> _state.update {
                it.copy(scrollEvent = null)
            }

            is NewsListAction.OnScrollChanged -> _state.update {
                it.copy(isAtTop = action.isAtTop)
            }

            is NewsListAction.OnRefreshed -> _state.update {
                it.copy(isRefreshing = false)
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
                articlesFlow = repository.searchNews(query)
                    // filtering is necessary because API could return the same items on
                    // adjacent pages
                    .map { pagingData ->
                        val seenIds = mutableSetOf<Int>()
                        pagingData.filter { item ->
                            if (seenIds.contains(item.id)) false
                            else {
                                seenIds += item.id; true
                            }
                        }
                    }
                    .cachedIn(viewModelScope),
                scrollEvent = ScrollEvent.SCROLL_UP
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