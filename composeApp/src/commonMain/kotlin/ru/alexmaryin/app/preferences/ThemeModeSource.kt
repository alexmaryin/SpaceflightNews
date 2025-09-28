package ru.alexmaryin.app.preferences

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.alexmaryin.app.drawer.NewsAppTheme

class ThemeModeSource(
    private val prefs: Prefs
) {
    fun getThemeMode(): Flow<NewsAppTheme> {
        return prefs.data.map {
            val theme = it[stringPreferencesKey("theme")] ?: NewsAppTheme.SYSTEM.name
            NewsAppTheme.valueOf(theme)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun changeTheme(theme: NewsAppTheme) {
        return withContext(Dispatchers.IO) {
            try {
                prefs.edit {
                    it[stringPreferencesKey("theme")] = theme.name
                }
            } catch (e: Exception) {
                println("FAILED TO SAVE PREFERENCES ON THE DISK!")
                e.printStackTrace()
            }
        }
    }
}

@Composable
fun rememberThemeSource(prefs: Prefs) = remember { ThemeModeSource(prefs) }
