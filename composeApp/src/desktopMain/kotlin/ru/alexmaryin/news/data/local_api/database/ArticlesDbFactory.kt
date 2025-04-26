package ru.alexmaryin.news.data.local_api.database

import androidx.room.Room
import androidx.room.RoomDatabase
import ru.alexmaryin.core.getAppDataDir
import java.io.File

actual class ArticlesDbFactory {
    actual fun create(): RoomDatabase.Builder<ArticlesDatabase> {
        val appDataDir = getAppDataDir()

        if (!appDataDir.exists()) appDataDir.mkdirs()

        val dbFile = File(appDataDir, ArticlesDatabase.DB_NAME)

        return Room.databaseBuilder(dbFile.absolutePath)
    }
}