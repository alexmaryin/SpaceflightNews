package ru.alexmaryin.news.ui.news_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import spaceflightnews.composeapp.generated.resources.scroll_to_start

@Composable
fun NewsListScreenRoot(
    viewModel: NewsListViewModel = koinViewModel(),
    onItemClick: (Article) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    NewsListScreen(
        state,
        onAction = { action ->
            if (action is NewsListAction.OnNewsItemClick) {
                onItemClick(action.article)
            } else {
                viewModel.onAction(action)
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
            modifier = Modifier.fillMaxWidth()
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
                onClick = { onAction(NewsListAction.OnScrollToStart) },
                colors = IconButtonDefaults.iconButtonColors().copy(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowUp,
                    contentDescription = stringResource(Res.string.scroll_to_start)
                )
            }
        }

        TabsBar(state.selectedTabIndex) { index ->
            onAction(NewsListAction.OnTabSelected(index))
        }

        NewsListPager(state, onAction)
    }
}

