package cvetyshayasiren.roughdraft.ui.features.draftBook

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil3.CoilImage
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftBookInteractions
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftPageEntity
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import cvetyshayasiren.roughdraft.ui.theme.title
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun DraftBookPageCard(
    modifier: Modifier = Modifier,
    page: DraftPageEntity,
    scaffoldNavigator: ThreePaneScaffoldNavigator<Any>,
    scope: CoroutineScope
) {
    val expanded = remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = DesignStyle.defaultPadding(),
                alignment = Alignment.Start
            )
        ) {
            CoilImage(
                modifier = Modifier
                    .weight(.3f)
                    .clip(shape = MaterialShapes.Pill.toShape())
                    .shadow(elevation = 2.dp),
                imageModel = { page.iconUri }
            )
            
            TextButton(
                modifier = Modifier.weight(.7f),
                onClick = {
                    DraftBookInteractions.setPage(page.name)
                    scope.launch { scaffoldNavigator.navigateTo(SupportingPaneScaffoldRole.Main) }

                }
            ) {
                Text(page.name)
            }
        }
        AnimatedVisibility(
            visible = expanded.value
        ) {
            Box(
                modifier = Modifier.height(400.dp)
            ) {
                Text(text = "MAP", style = MaterialTheme.typography.title())
            }
        }
    }
}