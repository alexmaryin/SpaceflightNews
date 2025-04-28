/*
 * Copyright (c) 2025.
 */

package ru.alexmaryin.news.ui.news_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.alexmaryin.core.ui.components.SurfaceIAText
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.easter_caption
import spaceflightnews.composeapp.generated.resources.universe_edge

@Composable
fun EasterEgg() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SurfaceIAText(
            text = stringResource(Res.string.easter_caption),
            modifier = Modifier.padding(16.dp)
        )
        Image(
            painter = painterResource(Res.drawable.universe_edge),
            contentDescription = null,
            modifier = Modifier.size(150.dp).padding(8.dp)
        )
    }
}