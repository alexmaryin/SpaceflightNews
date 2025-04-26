package ru.alexmaryin.app.drawer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.jetbrains.compose.resources.getString
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.app_version

class DrawerViewModel(
    rootTheme: NewsAppTheme
) : ViewModel() {

    private val _state = MutableStateFlow(DrawerState(colorTheme = rootTheme))
    val state = _state.onStart {
            val version = getString(Res.string.app_version)
            _state.update { it.copy(appVersion = version) }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value)

    fun onAction(action: DrawerAction) {
        when (action) {
            DrawerAction.OpenDrawer -> _state.update {
                it.copy(opened = true)
            }

            DrawerAction.CloseDrawer -> _state.update {
                it.copy(opened = false)
            }

            is DrawerAction.ChangeTheme -> _state.update {
                it.copy(colorTheme = action.newTheme)
            }

            else -> Unit
        }
    }
}

