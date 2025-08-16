package cvetyshayasiren.roughdraft.ui.adaptive

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.PaneScaffoldDirective
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                    modifier = Modifier.padding(DesignStyle.bigPadding())
                )
            }
                   },
        supportingPane = {
            AnimatedPane(
                modifier = Modifier
                    .safeContentPadding()
            ) {
                DraftBookView(
                    scaffoldNavigator = scaffoldNavigator
                )
            }
        }
    )
}