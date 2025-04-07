package ru.alexmaryin

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import ru.alexmaryin.news.domain.models.Article
import ru.alexmaryin.news.domain.models.Author
import ru.alexmaryin.news.ui.news_list.NewsListScreen
import ru.alexmaryin.news.ui.news_list.NewsListScreenRoot
import ru.alexmaryin.news.ui.news_list.NewsListState
import ru.alexmaryin.news.ui.news_list.NewsListViewModel
import kotlin.random.Random

@Composable
@Preview
fun App() {
    MaterialTheme {
        NewsListScreenRoot(viewModel = NewsListViewModel()) {  }
    }
}