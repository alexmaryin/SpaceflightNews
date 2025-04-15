package ru.alexmaryin.news.data.local_api.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteArticlesDAO {

    @Upsert
    suspend fun upsert(article: ArticleEntity)

    @Query("SELECT * FROM ArticleEntity")
    fun getFavouritesArticles(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM ArticleEntity WHERE id=:id")
    suspend fun getFavouriteArticleById(id: Int): ArticleEntity?

    @Query("DELETE FROM ArticleEntity WHERE id=:id")
    suspend fun deleteFavouriteArticle(id: Int)
}