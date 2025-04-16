package ru.alexmaryin.news.data.local_api.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object ArticlesDbConstructor : RoomDatabaseConstructor<ArticlesDatabase> {
    override fun initialize(): ArticlesDatabase
}