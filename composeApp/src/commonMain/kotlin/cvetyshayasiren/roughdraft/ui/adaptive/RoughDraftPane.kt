package cvetyshayasiren.roughdraft.ui.adaptive

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftBookInteractions
import cvetyshayasiren.roughdraft.ui.features.draftBook.DraftBookView
import cvetyshayasiren.roughdraft.ui.features.draftPage.DraftPageView
import cvetyshayasiren.roughdraft.ui.navigation.coloredBorder
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun RoughDraftPaneView(
    modifier: Modifier = Modifier,
    scaffoldNavigator: ThreePaneScaffoldNavigator<Any>
) {
    SupportingPaneScaffold(
        modifier = modifier,
        directive = scaffoldNavigator.scaffoldDirective,
        value = scaffoldNavigator.scaffoldValue,
        mainPane = {
            AnimatedPane(
                modifier = Modifier
                    .safeContentPadding()
            ) {
                DraftPageView(
                    modifier = Modifier.coloredBorder(color = Color.Blue),
                    scaffoldNavigator = scaffoldNavigator
                )
            }
                   },
        supportingPane = {
            AnimatedPane(
                modifier = Modifier
                    .coloredBorder(color = Color.Red)
                    .safeContentPadding()
            ) {
                DraftBookView(
                    modifier = Modifier
                        .coloredBorder(color = Color.Gray),
                    scaffoldNavigator = scaffoldNavigator
                )
            }
        }
    )
}