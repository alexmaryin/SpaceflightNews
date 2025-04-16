package ru.alexmaryin.di

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.alexmaryin.core.data.HttpClientFactory
import ru.alexmaryin.news.data.local_api.database.ArticlesDatabase
import ru.alexmaryin.news.data.local_api.database.ArticlesDbFactory
import ru.alexmaryin.news.data.remote_api.KtorRemoteNewsDataSource
import ru.alexmaryin.news.data.remote_api.NewsPagingSource
import ru.alexmaryin.news.data.remote_api.RemoteNewsDataSource
import ru.alexmaryin.news.data.repository.DefaultSpaceNewsRepository
import ru.alexmaryin.news.domain.SpaceNewsRepository
import ru.alexmaryin.news.domain.models.Article
import ru.alexmaryin.news.ui.SelectedArticleViewModel
import ru.alexmaryin.news.ui.article_details.ArticleDetailsViewModel
import ru.alexmaryin.news.ui.news_list.NewsListViewModel

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteNewsDataSource).bind<RemoteNewsDataSource>()
    singleOf(::DefaultSpaceNewsRepository).bind<SpaceNewsRepository>()

    single {
        get<ArticlesDbFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<ArticlesDatabase>().dao }

    single {
        Pager<Int, Article>(
            config = PagingConfig(
                pageSize = RemoteNewsDataSource.DEFAULT_LIMIT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPagingSource(get()) }
        )
    }

    viewModelOf(::NewsListViewModel)
    viewModelOf(::SelectedArticleViewModel)
    viewModelOf(::ArticleDetailsViewModel)
}