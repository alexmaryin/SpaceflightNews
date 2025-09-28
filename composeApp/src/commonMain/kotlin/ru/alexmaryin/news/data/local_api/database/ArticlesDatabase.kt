package ru.alexmaryin.news.data.local_api.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        LaunchEntity::class,
        EventEntity::class,
        AuthorEntity::class,
        ArticleEntity::class
    ],
    version = 1,
    exportSchema = true
)
@ConstructedBy(ArticlesDbConstructor::class)
abstract class ArticlesDatabase : RoomDatabase() {
    abstract val dao: ArticlesDAO

    companion object {
        const val DB_NAME = "articles.db"
    }
}