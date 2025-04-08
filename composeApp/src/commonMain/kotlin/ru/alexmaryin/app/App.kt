package ru.alexmaryin.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import ru.alexmaryin.news.ui.SelectedArticleViewModel
import ru.alexmaryin.news.ui.news_list.NewsListScreenRoot
import ru.alexmaryin.news.ui.news_list.NewsListViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Navigation.NewsGraph) {
            navigation<Navigation.NewsGraph>(
                startDestination = Navigation.NewsScreen
            ) {
                composable<Navigation.NewsScreen> {
                    val selectedArticleViewModel =
                        it.sharedKoinViewModel<SelectedArticleViewModel>(navController)

                    LaunchedEffect(true) {
                        selectedArticleViewModel.onSelectArticle(null)
                    }

                    NewsListScreenRoot(
                        viewModel = koinViewModel<NewsListViewModel>()
                    ) { article ->
                        selectedArticleViewModel.onSelectArticle(article)
                        navController.navigate(Navigation.ArticleDetails(article.id))
                    }
                }
                composable<Navigation.ArticleDetails> {
                    val selectedArticleViewModel =
                        it.sharedKoinViewModel<SelectedArticleViewModel>(navController)
                    val selectedArticle = selectedArticleViewModel
                        .selectedArticle.collectAsStateWithLifecycle()

                    LaunchedEffect(true) {
                        delay(5000L)
                        navController.navigateUp()
                    }

                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Article $selectedArticle")
                    }
                }
            }
        }
    }
}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(viewModelStoreOwner = parentEntry)
}