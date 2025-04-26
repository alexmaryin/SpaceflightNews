package ru.alexmaryin.news.ui.news_list.side_menu

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
import org.jetbrains.compose.resources.stringResource
import ru.alexmaryin.core.ui.components.SurfaceIAText
import ru.alexmaryin.core.ui.components.SurfaceText
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.app_name
import spaceflightnews.composeapp.generated.resources.app_version
import spaceflightnews.composeapp.generated.resources.close_side_menu
import spaceflightnews.composeapp.generated.resources.open_about

@Composable
fun SideMenu(
    opened: Boolean,
    onAction: (SideMenuAction) -> Unit,
    content: @Composable () -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    LaunchedEffect(opened) {
        if (opened) drawerState.open() else drawerState.close()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.primaryContainer,
                drawerContentColor = MaterialTheme.colorScheme.onSurface,
            ) {
                SurfaceText(stringResource(Res.string.app_name),
                    modifier = Modifier.padding(16.dp))
                HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                NavigationDrawerItem(
                    label = { Text(stringResource(Res.string.close_side_menu)) },
                    icon = { Icon(Icons.Outlined.Close, null) },
                    onClick = { onAction(SideMenuAction.CloseSideMenu) },
                    selected = false
                )
                NavigationDrawerItem(
                    label = { Text(stringResource(Res.string.open_about)) },
                    icon = { Icon(Icons.Outlined.Info, null) },
                    onClick = {
                        onAction(SideMenuAction.AboutClicked)
                        onAction(SideMenuAction.CloseSideMenu)
                    },
                    selected = false
                )

                Spacer(Modifier.weight(1f))

                val version = stringResource(Res.string.app_version)
                SurfaceIAText("v$version", modifier = Modifier.padding(16.dp))
            }
        },
        modifier = Modifier.systemBarsPadding(),
        content = content
    )
}