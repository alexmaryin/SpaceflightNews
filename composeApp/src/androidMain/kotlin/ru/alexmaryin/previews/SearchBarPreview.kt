package ru.alexmaryin.previews

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.alexmaryin.core.ui.components.SearchBar

@Preview(showBackground = true)
@Composable
private fun SearchBarPreview() {
    MaterialTheme {
        SearchBar(
            query = "",
            onQueryChange = {},
            modifier = Modifier
        )
    }
}