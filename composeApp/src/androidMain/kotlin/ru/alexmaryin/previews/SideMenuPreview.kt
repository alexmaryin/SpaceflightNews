package ru.alexmaryin.previews

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.alexmaryin.app.drawer.NewsAppTheme
import ru.alexmaryin.app.drawer.SideMenu

@Preview
@Composable
fun SideMenuPreview() {
    Surface(Modifier.fillMaxSize()) {
        SideMenu(
            state = DrawerState(DrawerValue.Open),
            selectedTheme = NewsAppTheme.LIGHT,
            onAction = {},
            {},
        )
    }
}