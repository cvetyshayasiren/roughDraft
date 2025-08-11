package cvetyshayasiren.roughdraft.ui.features.draftBook

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil3.CoilImage
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftPageEntity
import cvetyshayasiren.roughdraft.ui.navigation.coloredBorder
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import cvetyshayasiren.roughdraft.ui.theme.basicText
import cvetyshayasiren.roughdraft.ui.theme.smallText

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun DraftBookPageCard(
    modifier: Modifier = Modifier,
    page: DraftPageEntity
) {
    val expanded = remember { mutableStateOf(false) }
    val animatedDegree = animateFloatAsState(if(expanded.value) 180f else 0f)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(DesignStyle.bigPadding()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = DesignStyle.smallPadding(),
                alignment = Alignment.Start
            )
        ) {
            CoilImage(
                modifier = Modifier
                    .weight(.3f)
                    .clip(shape = DesignStyle.customShape)
                    .shadow(elevation = DesignStyle.shadowElevation)
                    .coloredBorder(page.color),
                imageModel = { page.iconUri }
            )

            Column(
                modifier = Modifier.weight(.6f),
                verticalArrangement = Arrangement.spacedBy(DesignStyle.smallPadding(), Alignment.CenterVertically),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = page.name,
                    style = MaterialTheme.typography.basicText()
                )
                Text(
                    text = page.prettyDate,
                    style = MaterialTheme.typography.smallText(
                        fontWeight = FontWeight.Light
                    )
                )
            }
            IconButton(
                modifier = Modifier.weight(.1f),
                onClick = {
                    expanded.value = !expanded.value
                }
            ) {
                Icon(
                    modifier = Modifier.rotate(animatedDegree.value),
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "switch show map icon"
                )
            }
        }
        AnimatedVisibility(
            visible = expanded.value
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                StaticMiniMapView(
                    modifier = Modifier.fillMaxSize(),
                    page = page
                )
            }
        }
    }
}