package cvetyshayasiren.roughdraft.ui.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import cvetyshayasiren.roughdraft.Config
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftBookInteractions
import cvetyshayasiren.roughdraft.ui.adaptive.RoughDraftPaneView
import cvetyshayasiren.roughdraft.ui.features.audioPlayer.AudioPlayerView
import cvetyshayasiren.roughdraft.ui.features.draftBook.DraftBookView
import cvetyshayasiren.roughdraft.ui.features.mapDraftBook.MapDraftBookView
import cvetyshayasiren.roughdraft.ui.features.settings.SettingsView
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun RoughDraftAdaptiveNavigation(modifier: Modifier = Modifier) {
    val currentDestination: MutableState<RoughDraftDestination> = rememberSaveable {
        mutableStateOf(RoughDraftDestination.DraftPane)
    }
    val scaffoldNavigator = rememberSupportingPaneScaffoldNavigator()
    val hazeState = rememberHazeState()

    NavigationSuiteScaffold(
        modifier = modifier,
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
                    selected = destination == currentDestination.value,
                    onClick = { currentDestination.value = destination }
                )
            }
        }
    ) {
        AnimatedContent(
            targetState = currentDestination.value
        ) { state ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                val currentPage = DraftBookInteractions.currentPage.collectAsState()

                Box(
                    modifier = Modifier.hazeSource(hazeState)
                ) {
                    when (state) {
                        RoughDraftDestination.DraftPane -> RoughDraftPaneView(
                            scaffoldNavigator = scaffoldNavigator,
                            currentDestination = currentDestination
                        )

                        RoughDraftDestination.DraftBook -> DraftBookView(
                            currentDestination = currentDestination
                        )

                        RoughDraftDestination.MapDraftBook -> MapDraftBookView()
                        RoughDraftDestination.Settings -> SettingsView()
                    }
                }
                AudioPlayerView(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(DesignStyle.smallPlusBigPadding())
                        .clip(DesignStyle.roundedShape)
                        .hazeEffect(
                            state = hazeState,
                            style = HazeMaterials.thin(
                                containerColor = currentPage.value.color
                            )
                        )
                        .widthIn(max = Config.MAX_PLAYER_WIDTH)
                        .heightIn(max = Config.MAX_PLAYER_HEIGHT)
                )
            }
        }
    }
}





