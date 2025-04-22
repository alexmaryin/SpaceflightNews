package ru.alexmaryin.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.alexmaryin.news.ui.about.AboutScreen
import ru.alexmaryin.news.ui.about.AboutState

@Preview
@Composable
fun AboutPreview() {
    AboutScreen(AboutState()) {}
}