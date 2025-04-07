package ru.alexmaryin

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.ktor.client.engine.cio.CIO
import org.jetbrains.compose.ui.tooling.preview.Preview
import ru.alexmaryin.core.data.HttpClientFactory
import ru.alexmaryin.news.data.remote_api.KtorRemoteNewsDataSource
import ru.alexmaryin.news.data.repository.DefaultSpaceNewsRepository
import ru.alexmaryin.news.ui.news_list.NewsListScreenRoot
import ru.alexmaryin.news.ui.news_list.NewsListViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        NewsListScreenRoot(
            viewModel = NewsListViewModel(
                repository = DefaultSpaceNewsRepository(
                    remoteDataSource = KtorRemoteNewsDataSource(
                        httpClient = HttpClientFactory.create(
                            engine =    remember { CIO.create() }
                        )
                    )
                )
            )
        ) { }
    }
}