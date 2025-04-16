package ru.alexmaryin.news.data.local_api.database

import androidx.room.RoomDatabase

expect class ArticlesDbFactory {
    fun create(): RoomDatabase.Builder<ArticlesDatabase>
}