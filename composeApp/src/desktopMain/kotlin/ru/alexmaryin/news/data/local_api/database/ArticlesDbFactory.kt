package ru.alexmaryin.news.data.local_api.database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

actual class ArticlesDbFactory {
    actual fun create(): RoomDatabase.Builder<ArticlesDatabase> {
        val os = System.getProperty("os.name")?.lowercase() ?: ""
        val userHome = System.getProperty("user.home")
        val appDataDir = when {
            os.contains("win") -> File(System.getenv("APPDATA"), "Space news")
            os.contains("mac") -> File(userHome, "Library/Application Support/Space news")
            else -> File(userHome, ".local/share/Space news")
        }

        if (!appDataDir.exists()) appDataDir.mkdirs()

        val dbFile = File(appDataDir, ArticlesDatabase.DB_NAME)

        return Room.databaseBuilder(dbFile.absolutePath)
    }
}