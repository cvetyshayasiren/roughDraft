package cvetyshayasiren.roughdraft.ui.features.draftPage

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil3.CoilImage
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftBookInteractions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun DraftPageView(
    modifier: Modifier = Modifier,
    scaffoldNavigator: ThreePaneScaffoldNavigator<Any>
) {
    val currentPage = DraftBookInteractions.currentPage.collectAsState()
    val scope = rememberCoroutineScope()

    AnimatedContent(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        targetState = currentPage.value,
        contentAlignment = Alignment.TopStart
    ) { page ->
        Column(
            modifier = modifier,
        ) {
            Text(text = "DraftPageView")

            CoilImage(
                modifier = Modifier.size(200.dp),
                imageModel = { page.iconPath }
            )

            Text(page.name)
            HorizontalDivider()
            Text(page.prose)

            if (scaffoldNavigator.scaffoldValue[SupportingPaneScaffoldRole.Supporting] == PaneAdaptedValue.Hidden) {
                TextButton(
                    modifier = Modifier.wrapContentSize(),
                    onClick = {
                        scope.launch {
                            scaffoldNavigator.navigateTo(SupportingPaneScaffoldRole.Supporting)
                        }
                    }
                ) {
                    Text("Show supporting pane")
                }
            }
        }
    }
}