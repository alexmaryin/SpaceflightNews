package ru.alexmaryin.news.ui.news_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import ru.alexmaryin.core.ui.components.SearchBar
import ru.alexmaryin.news.domain.models.Article
import ru.alexmaryin.news.ui.news_list.components.NewsListPager
import ru.alexmaryin.news.ui.news_list.components.TabsBar
import ru.alexmaryin.news.ui.news_list.side_menu.SideMenu
import ru.alexmaryin.news.ui.news_list.side_menu.SideMenuAction
import spaceflightnews.composeapp.generated.resources.*

@Composable
fun NewsListScreenRoot(
    viewModel: NewsListViewModel = koinViewModel(),
    onItemClick: (Article) -> Unit,
    onAboutClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    NewsListScreen(
        state,
        onAction = { action ->
            when (action) {
                is NewsListAction.OnNewsItemClick -> onItemClick(action.article)
                else -> viewModel.onAction(action)
            }
        },
        onSideMenuAction = { action ->
            when (action) {
                is SideMenuAction.AboutClicked -> onAboutClick()
                else -> viewModel.onSideMenuAction(action)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListScreen(
    state: NewsListState,
    onAction: (NewsListAction) -> Unit,
    onSideMenuAction: (SideMenuAction) -> Unit
) {
    SideMenu(
        opened = state.sideMenuOpened,
        onAction = { action -> onSideMenuAction(action) }
    ) {
        val scrolledUp = state.scrollState == ScrollState.SCROLLED_UP

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth().systemBarsPadding()
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = { onSideMenuAction(SideMenuAction.OpenSideMenu) },
                        colors = IconButtonDefaults.iconButtonColors().copy(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 4.dp)
                    ) {
                        val iconDescription =
                            if (state.sideMenuOpened) Res.string.close_side_menu else Res.string.open_side_menu
                        Icon(
                            imageVector = Icons.Outlined.Menu,
                            contentDescription = stringResource(iconDescription)
                        )
                    }

                    SearchBar(
                        query = state.searchQuery,
                        onQueryChange = { onAction(NewsListAction.OnSearchQueryChange(it)) },
                        modifier = Modifier.widthIn(max = 600.dp)
                            .weight(1f, fill = false)
                            .padding(8.dp)
                    )

                    IconButton(
                        onClick = {
                            onAction(
                                if (scrolledUp) NewsListAction.OnRefresh else NewsListAction.OnScrollToStart
                            )
                        },
                        colors = IconButtonDefaults.iconButtonColors().copy(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 4.dp)
                    ) {
                        Icon(
                            imageVector = if (scrolledUp) Icons.Filled.Refresh
                            else Icons.Filled.KeyboardArrowUp,
                            contentDescription = stringResource(
                                if (scrolledUp) Res.string.refresh_articles else Res.string.scroll_to_start
                            )
                        )
                    }
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier.padding(padding)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TabsBar(state.selectedTabIndex) { index ->
                    onAction(NewsListAction.OnTabSelected(index))
                }

                NewsListPager(state, onAction)
            }
        }
    }
}

