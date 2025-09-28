package ru.alexmaryin.news.data.local_api.database

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual class ArticlesDbFactory {
    actual fun create(): RoomDatabase.Builder<ArticlesDatabase> {
        val dbPath = documentsPath() + ArticlesDatabase.DB_NAME
        return Room.databaseBuilder<ArticlesDatabase>(
            name = dbPath
        )
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun documentsPath(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}