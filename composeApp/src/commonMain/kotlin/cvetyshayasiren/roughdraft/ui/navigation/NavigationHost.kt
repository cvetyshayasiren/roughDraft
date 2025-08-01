package cvetyshayasiren.roughdraft.ui.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Domain
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cvetyshayasiren.roughdraft.ui.adaptive.RoughDraftPaneView
import cvetyshayasiren.roughdraft.ui.features.draftPage.DraftPageView
import cvetyshayasiren.roughdraft.ui.features.mapDraftBook.MapDraftBookView
import cvetyshayasiren.roughdraft.ui.features.settings.SettingsView

fun Modifier.coloredBorder(color: Color): Modifier {
    return this then Modifier.border(width = 2.dp, color = color)
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun RoughDraftAdaptiveNavigation(modifier: Modifier = Modifier) {
    var currentDestination: RoughDraftDestination by rememberSaveable {
        mutableStateOf(RoughDraftDestination.DraftPane)
    }

    NavigationSuiteScaffold(
        modifier = modifier,
        navigationSuiteItems = {
            RoughDraftDestination.entries.forEach { destination ->
                item(
                    icon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = destination.contentDescription
                        )
                    },
                    label = { Text(destination.label) },
                    selected = destination == currentDestination,
                    onClick = { currentDestination = destination }
                )
            }
        }
    ) {
        AnimatedContent(
            modifier = Modifier.fillMaxSize(),
            targetState = currentDestination
        ) { state ->
            when(state) {
                RoughDraftDestination.DraftPane -> RoughDraftPaneView(modifier = Modifier.coloredBorder(Color.Green))
                RoughDraftDestination.MapDraftBook -> MapDraftBookView(modifier = Modifier.coloredBorder(Color.Red))
                RoughDraftDestination.Settings -> SettingsView(modifier = Modifier.coloredBorder(Color.Blue))
            }
        }
    }
}





