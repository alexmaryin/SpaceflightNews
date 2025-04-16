package ru.alexmaryin.news.data.local_api.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class ArticlesDbFactory(
    private val context: Context
) {
    actual fun create(): RoomDatabase.Builder<ArticlesDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(ArticlesDatabase.DB_NAME)
        return Room.databaseBuilder(appContext, dbFile.absolutePath)
    }
}