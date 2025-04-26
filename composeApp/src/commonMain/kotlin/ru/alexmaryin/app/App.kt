package ru.alexmaryin.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import org.koin.core.parameter.parametersOf
import ru.alexmaryin.app.drawer.DrawerAction
import ru.alexmaryin.app.drawer.DrawerViewModel
import ru.alexmaryin.app.drawer.NewsAppTheme
import ru.alexmaryin.app.drawer.SideMenuRoot
import ru.alexmaryin.app.preferences.rememberPrefs
import ru.alexmaryin.app.preferences.rememberThemeSource
import ru.alexmaryin.core.ui.theme.spaceNewsDarkScheme
import ru.alexmaryin.core.ui.theme.spaceNewsLightScheme
import ru.alexmaryin.news.ui.SelectedArticleViewModel
import ru.alexmaryin.news.ui.about.AboutScreenRoot
import ru.alexmaryin.news.ui.about.AboutViewModel
import ru.alexmaryin.news.ui.article_details.ArticleDetailsAction
import ru.alexmaryin.news.ui.article_details.ArticleDetailsViewModel
import ru.alexmaryin.news.ui.article_details.ArticlesDetailsScreenRoot
import ru.alexmaryin.news.ui.news_list.NewsListScreenRoot
import ru.alexmaryin.news.ui.news_list.NewsListViewModel

@Composable
@Preview
fun App() {
    val prefs = rememberPrefs()
    val themeSource = rememberThemeSource(prefs)
    val theme by themeSource.getThemeMode().collectAsStateWithLifecycle(NewsAppTheme.SYSTEM)
    val isDarkTheme = theme == NewsAppTheme.DARK || (theme == NewsAppTheme.SYSTEM && isSystemInDarkTheme())

    val colors = if (isDarkTheme) spaceNewsDarkScheme else spaceNewsLightScheme
    MaterialTheme(
        colorScheme = colors
    ) {
        val drawerViewModel = koinViewModel<DrawerViewModel> { parametersOf(theme) }
        LaunchedEffect(theme) {
            drawerViewModel.onAction(DrawerAction.ChangeTheme(theme))
        }

        val navController = rememberNavController()
        SideMenuRoot(
            viewModel = drawerViewModel,
            onAboutClick = { navController.navigate(Navigation.AboutScreen) },
            onThemeChange = { newTheme -> themeSource.changeTheme(newTheme) }
        ) {
            NavHost(navController = navController, startDestination = Navigation.NewsGraph) {
                navigation<Navigation.NewsGraph>(startDestination = Navigation.NewsScreen) {
                    composable<Navigation.NewsScreen> { entry ->
                        val selectedArticleViewModel =
                            entry.sharedKoinViewModel<SelectedArticleViewModel>(navController)

                        LaunchedEffect(true) {
                            selectedArticleViewModel.onSelectArticle(null)
                        }

                        NewsListScreenRoot(
                            viewModel = koinViewModel<NewsListViewModel>(),
                            onItemClick = { article ->
                                selectedArticleViewModel.onSelectArticle(article)
                                navController.navigate(Navigation.ArticleDetails(article.id))
                            },
                            onSideMenuClick = {
                                drawerViewModel.onAction(DrawerAction.OpenDrawer)
                            }
                        )
                    }

                    composable<Navigation.ArticleDetails> { entry ->
                        val selectedArticleViewModel =
                            entry.sharedKoinViewModel<SelectedArticleViewModel>(navController)
                        val selectedArticle by selectedArticleViewModel
                            .selectedArticle.collectAsStateWithLifecycle()
                        val viewModel = koinViewModel<ArticleDetailsViewModel>()

                        LaunchedEffect(selectedArticle) {
                            selectedArticle?.let {
                                viewModel.onAction(ArticleDetailsAction.onSelectedArticleChange(it))
                            }
                        }

                        ArticlesDetailsScreenRoot(
                            viewModel = viewModel
                        ) { navController.navigateUp() }
                    }

                    composable<Navigation.AboutScreen> { entry ->
                        val viewModel = koinViewModel<AboutViewModel>()
                        AboutScreenRoot(viewModel) { navController.navigateUp() }
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