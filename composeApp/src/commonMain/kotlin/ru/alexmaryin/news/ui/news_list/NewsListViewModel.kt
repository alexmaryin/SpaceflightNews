package ru.alexmaryin.news.ui.news_list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewsListViewModel : ViewModel() {

    private val _state = MutableStateFlow(NewsListState())
    val state = _state.asStateFlow()

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
}