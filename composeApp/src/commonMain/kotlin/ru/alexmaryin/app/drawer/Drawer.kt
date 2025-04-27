package ru.alexmaryin.app.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import ru.alexmaryin.core.ui.components.SurfaceIAText
import ru.alexmaryin.core.ui.components.SurfaceText
import spaceflightnews.composeapp.generated.resources.*

@Composable
fun SideMenuRoot(
    viewModel: DrawerViewModel,
    drawerState: DrawerState,
    onAboutClick: () -> Unit,
    onThemeChange: suspend (NewsAppTheme) -> Unit,
    content: @Composable (() -> Unit),
) {
    val scope = rememberCoroutineScope()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(drawerState) {
        snapshotFlow { drawerState.isOpen }
            .distinctUntilChanged()
            .collect { isOpen ->
                viewModel.onAction(DrawerAction.SideMenuChange(isOpen = isOpen))
            }
    }

    LaunchedEffect(state.opened) {
        if (state.opened) drawerState.open() else drawerState.close()
    }

    SideMenu(
        state = drawerState,
        selectedTheme = state.colorTheme,
        onAction = { action ->
            when (action) {
                DrawerAction.AboutClicked -> onAboutClick()
                is DrawerAction.ChangeTheme -> scope.launch { onThemeChange(action.newTheme) }
                else -> viewModel.onAction(action)
            }
        }
    ) {
        content()
    }
}


@Composable
fun SideMenu(
    state: DrawerState,
    selectedTheme: NewsAppTheme,
    onAction: (DrawerAction) -> Unit,
    content: @Composable () -> Unit,
) {

    val starBadge = @Composable { current: NewsAppTheme ->
        if (current != selectedTheme) Unit else
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Color.Yellow
            )
    }

    ModalNavigationDrawer(
        drawerState = state,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.primaryContainer,
                drawerContentColor = MaterialTheme.colorScheme.onSurface,
            ) {
                SurfaceText(
                    stringResource(Res.string.app_name),
                    modifier = Modifier.padding(16.dp)
                )
                HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                NavigationDrawerItem(
                    label = { Text(stringResource(Res.string.close_side_menu)) },
                    icon = { Icon(Icons.Outlined.Close, null) },
                    onClick = { onAction(DrawerAction.SideMenuChange(isOpen = false)) },
                    selected = false
                )
                NavigationDrawerItem(
                    label = { Text(stringResource(Res.string.open_about)) },
                    icon = { Icon(Icons.Outlined.Info, null) },
                    onClick = {
                        onAction(DrawerAction.AboutClicked)
                        onAction(DrawerAction.SideMenuChange(isOpen = false))
                    },
                    selected = false
                )
                HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                SurfaceText(
                    stringResource(Res.string.app_theme_caption),
                    modifier = Modifier.padding(16.dp)
                )
                NavigationDrawerItem(
                    label = { Text(stringResource(Res.string.app_theme_light)) },
                    onClick = { onAction(DrawerAction.ChangeTheme(NewsAppTheme.LIGHT)) },
                    selected = false,
                    badge = { starBadge(NewsAppTheme.LIGHT) }
                )
                NavigationDrawerItem(
                    label = { Text(stringResource(Res.string.app_theme_dark)) },
                    onClick = { onAction(DrawerAction.ChangeTheme(NewsAppTheme.DARK)) },
                    selected = false,
                    badge = { starBadge(NewsAppTheme.DARK) }
                )
                NavigationDrawerItem(
                    label = { Text(stringResource(Res.string.app_theme_system)) },
                    onClick = { onAction(DrawerAction.ChangeTheme(NewsAppTheme.SYSTEM)) },
                    selected = false,
                    badge = { starBadge(NewsAppTheme.SYSTEM) }
                )

                Spacer(Modifier.weight(1f))

                val version = stringResource(Res.string.app_version)
                SurfaceIAText("v$version", modifier = Modifier.padding(16.dp))
            }
        },
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .systemBarsPadding()
    ) {
        content()
    }
}