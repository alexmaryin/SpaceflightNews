package ru.alexmaryin.app.preferences

import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

typealias Prefs = DataStore<Preferences>

fun createDataStore(producePath: () -> String): Prefs =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() }
    )

internal const val PREFS_FILE = ".preferences_pb"

@Composable
expect fun rememberPrefs(): Prefs