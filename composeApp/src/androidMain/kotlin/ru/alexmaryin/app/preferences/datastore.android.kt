package ru.alexmaryin.app.preferences

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberPrefs(): Prefs {
    val context = LocalContext.current
    return remember {
        createDataStore {
            context.filesDir.resolve(PREFS_FILE).absolutePath
        }
    }
}