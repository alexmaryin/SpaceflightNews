package ru.alexmaryin.news.ui.news_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.app_name
import spaceflightnews.composeapp.generated.resources.refresh_articles
import spaceflightnews.composeapp.generated.resources.scroll_to_start

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
                is NewsListAction.AboutClicked -> onAboutClick()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun NewsListScreen(
    state: NewsListState,
    onAction: (NewsListAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.primaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchBar(
                query = state.searchQuery,
                onQueryChange = {
                    onAction(NewsListAction.OnSearchQueryChange(it))
                },
                modifier = Modifier.widthIn(max = 600.dp)
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp)
            )

            IconButton(
                onClick = { onAction(NewsListAction.AboutClicked) },
                colors = IconButtonDefaults.iconButtonColors().copy(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = stringResource(Res.string.app_name)
                )
            }

            val scrolledUp = state.scrollState == ScrollState.SCROLLED_UP

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

        TabsBar(state.selectedTabIndex) { index ->
            onAction(NewsListAction.OnTabSelected(index))
        }

        NewsListPager(state, onAction)
    }
}

