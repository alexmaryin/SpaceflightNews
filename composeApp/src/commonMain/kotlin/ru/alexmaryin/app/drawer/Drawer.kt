package ru.alexmaryin.app.drawer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import ru.alexmaryin.core.ui.components.SurfaceIAText
import ru.alexmaryin.core.ui.components.SurfaceText
import spaceflightnews.composeapp.generated.resources.*

@Composable
fun SideMenuRoot(
    viewModel: DrawerViewModel,
    onAboutClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    SideMenu(
        opened = state.value.opened,
        onAction = { action ->
            if (action is DrawerAction.AboutClicked) onAboutClick() else viewModel.onAction(action)
        }
    ) {
        content()
    }
}


@Composable
fun SideMenu(
    opened: Boolean,
    onAction: (DrawerAction) -> Unit,
    content: @Composable () -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    LaunchedEffect(opened) {
        if (opened) drawerState.open() else drawerState.close()
    }
    LaunchedEffect(drawerState.isClosed) {
        if (drawerState.isClosed && opened) onAction(DrawerAction.CloseDrawer)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
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
                    onClick = { onAction(DrawerAction.CloseDrawer) },
                    selected = false
                )
                NavigationDrawerItem(
                    label = { Text(stringResource(Res.string.open_about)) },
                    icon = { Icon(Icons.Outlined.Info, null) },
                    onClick = {
                        onAction(DrawerAction.AboutClicked)
                        onAction(DrawerAction.CloseDrawer)
                    },
                    selected = false
                )

                Spacer(Modifier.weight(1f))

                val version = stringResource(Res.string.app_version)
                SurfaceIAText("v$version", modifier = Modifier.padding(16.dp))
            }
        },
        modifier = Modifier.systemBarsPadding()
    ) {
        content()
    }
}