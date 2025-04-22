package ru.alexmaryin.news.ui.about

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import ru.alexmaryin.core.ui.components.SurfaceIAText
import ru.alexmaryin.core.ui.components.SurfaceText
import spaceflightnews.composeapp.generated.resources.*

@Composable
fun AboutScreenRoot(
    viewModel: AboutViewModel,
    onBackClick: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    AboutScreen(state.value) { action ->
        if (action is AboutAction.Back) onBackClick() else viewModel.onAction(action)
    }
}

@Composable
fun AboutScreen(
    state: AboutState,
    onAction: (AboutAction) -> Unit
) {
    val scrollState = rememberScrollState()
    var expandedList by remember { mutableStateOf(false) }
    val toggleExpandedList = { expandedList = !expandedList }
    val arrowRotation by animateFloatAsState(if (expandedList) 90f else 0f, label = "expanded arrow")
    val uriHandler = LocalUriHandler.current

    Surface(
        modifier = Modifier.fillMaxSize()
            .safeContentPadding(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.app_name),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
            )
            HorizontalDivider(modifier = Modifier.padding(16.dp).clip(RoundedCornerShape(50)))
            SurfaceIAText(
                text = stringResource(Res.string.app_slogan),
                alpha = 0.66f
            )
            HorizontalDivider(modifier = Modifier.padding(16.dp).clip(RoundedCornerShape(50)))
            SurfaceText(stringResource(Res.string.app_description))
            HorizontalDivider(modifier = Modifier.padding(16.dp).clip(RoundedCornerShape(50)))
            Row(
                modifier = Modifier.fillMaxWidth().clickable(
                    onClick = toggleExpandedList
                )
            ) {
                SurfaceText(stringResource(Res.string.app_source_list_caption), Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.rotate(arrowRotation)
                )
            }
            AnimatedVisibility(expandedList) {
                SurfaceText(
                    stringResource(Res.string.app_source_list),
                    modifier = Modifier.clickable(onClick = toggleExpandedList)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainer,
                            shape = RoundedCornerShape(16.dp)
                        )
                )
            }
            HorizontalDivider(modifier = Modifier.padding(16.dp).clip(RoundedCornerShape(50)))
            SurfaceText(stringResource(Res.string.app_contact))
            SurfaceText(
                text = state.contactInfo,
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(100)
                )
                    .clickable(onClick = { uriHandler.openUri("mailto://${state.contactInfo}") })
            )
            HorizontalDivider(modifier = Modifier.padding(16.dp).clip(RoundedCornerShape(50)))
            SurfaceText(
                text = stringResource(Res.string.app_url_caption),
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(100)
                )
                    .clickable(onClick = { uriHandler.openUri(state.url) })
            )
            HorizontalDivider(modifier = Modifier.padding(16.dp).clip(RoundedCornerShape(50)))
            SurfaceText(
                text = stringResource(Res.string.app_privacy_policy_caption),
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(100)
                )
                    .clickable(onClick = { uriHandler.openUri(state.privacyPolicyUrl) })
            )
            HorizontalDivider(modifier = Modifier.padding(16.dp).clip(RoundedCornerShape(50)))
            SurfaceText(stringResource(Res.string.app_author))
            HorizontalDivider(modifier = Modifier.padding(16.dp).clip(RoundedCornerShape(50)))
            Button(
                onClick = { onAction(AboutAction.Back) }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = null
                )
            }
        }
    }
}
