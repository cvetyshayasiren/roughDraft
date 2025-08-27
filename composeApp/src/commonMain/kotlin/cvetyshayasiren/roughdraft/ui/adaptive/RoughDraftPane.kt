package cvetyshayasiren.roughdraft.ui.adaptive

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import cvetyshayasiren.roughdraft.ui.features.draftBook.DraftBookView
import cvetyshayasiren.roughdraft.ui.features.draftPage.DraftPageView
import cvetyshayasiren.roughdraft.ui.navigation.RoughDraftDestination
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun RoughDraftPaneView(
    modifier: Modifier = Modifier,
    scaffoldNavigator: ThreePaneScaffoldNavigator<Any>,
    currentDestination: MutableState<RoughDraftDestination>
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
                DraftPageView()
            }
        },
        supportingPane = {
            AnimatedPane(
                modifier = Modifier
                    .safeContentPadding()
            ) {
                DraftBookView(currentDestination = currentDestination)
            }
        }
    )
}