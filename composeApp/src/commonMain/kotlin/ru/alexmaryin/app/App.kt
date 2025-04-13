package ru.alexmaryin.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import ru.alexmaryin.core.ui.theme.spaceNewsDarkScheme
import ru.alexmaryin.core.ui.theme.spaceNewsLightScheme
import ru.alexmaryin.news.ui.SelectedArticleViewModel
import ru.alexmaryin.news.ui.article_details.ArticleDetailsState
import ru.alexmaryin.news.ui.article_details.ArticlesDetailsScreen
import ru.alexmaryin.news.ui.news_list.NewsListScreenRoot
import ru.alexmaryin.news.ui.news_list.NewsListViewModel

@Composable
@Preview
fun App(
    darkTheme: Boolean = isSystemInDarkTheme()
) {
    val colors = if (darkTheme) spaceNewsDarkScheme else spaceNewsLightScheme
    MaterialTheme(
        colorScheme = colors
    ) {
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

                    ArticlesDetailsScreen(
                        state = ArticleDetailsState(article = selectedArticle.value),
                        onAction = { navController.navigateUp() } // TODO remove
                    )
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