package cvetyshayasiren.roughdraft.ui.adaptive

import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cvetyshayasiren.roughdraft.ui.features.draftList.DraftListView
import cvetyshayasiren.roughdraft.ui.features.draftPage.DraftPageView
import cvetyshayasiren.roughdraft.ui.navigation.coloredBorder

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun RoughDraftPaneView(
    modifier: Modifier = Modifier
) {
    val scaffoldNavigator = rememberSupportingPaneScaffoldNavigator()

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
                    .safeContentPadding()
            ) {
                DraftListView(
                    modifier = Modifier.coloredBorder(color = Color.Gray),
                    scaffoldNavigator = scaffoldNavigator
                )
            }
        }
    )
}