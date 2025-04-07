package ru.alexmaryin.news.ui.news_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import ru.alexmaryin.news.ui.news_list.Tabs
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.articles_tab
import spaceflightnews.composeapp.generated.resources.favourites_tab

@Composable
fun ColumnScope.TabsBar(
    selectedTabIndex: Int,
    onClick: (Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.padding(top = 12.dp)
                    .widthIn(max = 800.dp)
                    .fillMaxWidth()
            ) {
                // Articles tab
                Tab(
                    selected = selectedTabIndex == Tabs.ARTICLES_TAB,
                    onClick = { onClick(Tabs.ARTICLES_TAB) },
                    modifier = Modifier.weight(1f),
                    unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    text = { Text(stringResource(Res.string.articles_tab)) }
                )
                // Favourites tab
                Tab(
                    selected = selectedTabIndex == Tabs.FAVOURITES_TAB,
                    onClick = { onClick(Tabs.FAVOURITES_TAB) },
                    modifier = Modifier.weight(1f),
                    unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    text = { Text(stringResource(Res.string.favourites_tab)) }
                )
            }
        }
    }
}