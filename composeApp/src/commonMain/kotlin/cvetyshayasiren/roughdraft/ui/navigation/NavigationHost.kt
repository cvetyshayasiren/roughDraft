package cvetyshayasiren.roughdraft.ui.navigation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cvetyshayasiren.roughdraft.ui.adaptive.RoughDraftPaneView
import cvetyshayasiren.roughdraft.ui.features.mapDraftBook.MapDraftBookView
import cvetyshayasiren.roughdraft.ui.features.settings.SettingsView

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun RoughDraftNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        NavHost(
            modifier = modifier,
            contentAlignment = Alignment.Center,
            navController = navController,
            startDestination = RoughDraftDestination.DraftPane
        ) {
            composable<RoughDraftDestination.DraftPane> {
                RoughDraftPaneView(
                    modifier = Modifier.coloredBorder(Color.Green)
                )
            }
            composable<RoughDraftDestination.MapDraftList> {
                MapDraftBookView(
                    modifier = Modifier.coloredBorder(Color.Red)
                )
            }
            composable<RoughDraftDestination.Settings> {
                SettingsView(
                    modifier = Modifier.coloredBorder(Color.Blue)
                )
            }
        }

        NavCardView(
            modifier = Modifier
                .coloredBorder(Color.Cyan)
                .align(Alignment.TopCenter),
            navController = navController
        )
    }
}

fun Modifier.coloredBorder(color: Color): Modifier {
    return this then Modifier.border(width = 2.dp, color = color)
}





