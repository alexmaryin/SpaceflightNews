package ru.alexmaryin.previews

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.alexmaryin.news.ui.news_list.side_menu.SideMenu

@Preview
@Composable
fun SideMenuPreview() {
    Surface(Modifier.fillMaxSize()) {
        SideMenu(
            opened = true,
            onAction = {}
        ) {}
    }
}