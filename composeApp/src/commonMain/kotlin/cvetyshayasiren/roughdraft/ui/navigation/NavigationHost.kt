package cvetyshayasiren.roughdraft.ui.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.rememberNavigationSuiteScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cvetyshayasiren.roughdraft.ui.adaptive.RoughDraftPaneView
import cvetyshayasiren.roughdraft.ui.features.audioPlayer.AudioPlayerView
import cvetyshayasiren.roughdraft.ui.features.draftBook.DraftBookView
import cvetyshayasiren.roughdraft.ui.features.mapDraftBook.MapDraftBookView
import cvetyshayasiren.roughdraft.ui.features.settings.SettingsView
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun RoughDraftAdaptiveNavigation(modifier: Modifier = Modifier) {
    var currentDestination: RoughDraftDestination by rememberSaveable {
        mutableStateOf(RoughDraftDestination.DraftPane)
    }
    val scaffoldNavigator = rememberSupportingPaneScaffoldNavigator()
    val navigationSuiteScaffoldState = rememberNavigationSuiteScaffoldState()

    val hazeState = rememberHazeState()

    NavigationSuiteScaffold(
        modifier = modifier,
        state = navigationSuiteScaffoldState,
        navigationSuiteItems = {
            RoughDraftDestination.entries.forEach { destination ->
                if (destination == RoughDraftDestination.DraftBook &&
                    scaffoldNavigator.scaffoldValue[SupportingPaneScaffoldRole.Supporting] == PaneAdaptedValue.Expanded
                ) {
                    return@forEach
                }

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
            Box {
                Box(
                    modifier = Modifier.hazeSource(hazeState)
                ) {
                    when (state) {
                        RoughDraftDestination.DraftPane -> RoughDraftPaneView(
                            scaffoldNavigator = scaffoldNavigator
                        )

                        RoughDraftDestination.DraftBook -> DraftBookView(
                            scaffoldNavigator = scaffoldNavigator
                        )

                        RoughDraftDestination.MapDraftBook -> MapDraftBookView()
                        RoughDraftDestination.Settings -> SettingsView()
                    }
                }
                AudioPlayerView(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .hazeEffect(state = hazeState)
                        .padding(DesignStyle.smallPadding())
                )
            }
        }
    }
}





