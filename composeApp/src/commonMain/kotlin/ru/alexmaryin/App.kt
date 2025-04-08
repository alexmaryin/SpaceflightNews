package ru.alexmaryin

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import ru.alexmaryin.news.ui.news_list.NewsListScreenRoot
import ru.alexmaryin.news.ui.news_list.NewsListViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        NewsListScreenRoot(
            viewModel = koinViewModel<NewsListViewModel>()
        ) { }
    }
}