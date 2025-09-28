package ru.alexmaryin.app.preferences

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import ru.alexmaryin.core.getAppDataDir
import java.io.File

@Composable
actual fun rememberPrefs(): Prefs {
    val appDataDir = getAppDataDir()
    if (!appDataDir.exists()) appDataDir.mkdirs()
    return remember {
        val prefsFile = File(appDataDir, PREFS_FILE)
        createDataStore {
            prefsFile.absolutePath
        }
    }
}