package ru.alexmaryin.news.ui.about

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AboutViewModel : ViewModel() {

    private val _state = MutableStateFlow(AboutState())
    val state = _state.asStateFlow()

    fun onAction(action: AboutAction) {
        when (action) {
            AboutAction.Back -> Unit
        }
    }
}