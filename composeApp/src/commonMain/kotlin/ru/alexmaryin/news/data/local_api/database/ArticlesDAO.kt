package ru.alexmaryin.news.data.local_api.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDAO {

    @Transaction
    @Query("SELECT * FROM ArticleEntity ORDER BY id DESC")
    fun getAllArticles(): Flow<List<ArticleWithRelations>>

    @Transaction
    @Query("SELECT * FROM ArticleEntity WHERE isFavourite = true ORDER BY id DESC")
    fun getFavouriteArticles(): Flow<List<ArticleWithRelations>>

    @Transaction
    @Query("SELECT * FROM ArticleEntity WHERE id = :id")
    suspend fun getArticleById(id: Int): ArticleWithRelations?

    @Upsert
    suspend fun insertArticleData(article: ArticleEntity)

    @Upsert
    suspend fun insertAuthors(authors: List<AuthorEntity>)

    @Upsert
    suspend fun insertLaunches(launches: List<LaunchEntity>)

    @Upsert
    suspend fun insertEvents(events: List<EventEntity>)

    @Transaction
    suspend fun insertArticle(
        article: ArticleEntity,
        authors: List<AuthorEntity>,
        launches: List<LaunchEntity>,
        events: List<EventEntity>
    ) {
        insertArticleData(article)
        insertAuthors(authors)
        insertLaunches(launches)
        insertEvents(events)
    }

    @Transaction
    suspend fun insertFavouriteArticle(
        article: ArticleEntity,
        authors: List<AuthorEntity>,
        launches: List<LaunchEntity>,
        events: List<EventEntity>
    ) = insertArticle(article.copy(isFavourite = true), authors, launches, events)

    @Query("DELETE FROM ArticleEntity")
    suspend fun clearAll()

    @Query("DELETE FROM ArticleEntity WHERE id = :articleId")
    suspend fun deleteArticleById(articleId: Int)

    @Query("UPDATE ArticleEntity SET isFavourite = false WHERE id = :articleId")
    suspend fun deleteFromFavourites(articleId: Int)
}
