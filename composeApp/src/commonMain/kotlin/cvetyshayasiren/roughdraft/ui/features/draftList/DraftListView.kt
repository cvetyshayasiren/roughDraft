package cvetyshayasiren.roughdraft.ui.features.draftList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun DraftListView(
    modifier: Modifier = Modifier,
    scaffoldNavigator: ThreePaneScaffoldNavigator<Any>
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("DraftListView")
        if (scaffoldNavigator.scaffoldValue[SupportingPaneScaffoldRole.Main] == PaneAdaptedValue.Hidden) {
            TextButton(
                modifier = Modifier.wrapContentSize(),
                onClick = {
                    scope.launch {
                        scaffoldNavigator.navigateTo(SupportingPaneScaffoldRole.Main)
                    }
                }
            ) {
                Text("Show main pane")
            }
        }
    }
}