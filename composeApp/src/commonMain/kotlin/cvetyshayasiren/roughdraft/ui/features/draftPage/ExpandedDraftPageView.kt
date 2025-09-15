package cvetyshayasiren.roughdraft.ui.features.draftPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.github.panpf.sketch.AsyncImage
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftPageEntity
import cvetyshayasiren.roughdraft.domain.draftsInteractions.getComposeResourceUri
import cvetyshayasiren.roughdraft.domain.map.CustomMarkers
import cvetyshayasiren.roughdraft.domain.map.getMapState
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import cvetyshayasiren.roughdraft.ui.utils.blend.BackgroundMode
import cvetyshayasiren.roughdraft.ui.utils.blend.blend
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import ovh.plrapps.mapcompose.ui.MapUI

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun ExpandedDraftPageView(
    page: DraftPageEntity,
    modifier: Modifier = Modifier

) {
    val hazeState = rememberHazeState()
    val padding = DesignStyle.multiBigPadding(8)
    Column(
        verticalArrangement = Arrangement.spacedBy(space = padding, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .hazeSource(state = hazeState)
        ) {
           Row {
               AsyncImage(
                   modifier = Modifier.weight(1f),
                   uri = page.iconPath.getComposeResourceUri(),
                   contentDescription = "draft page image",
                   contentScale = ContentScale.FillWidth
               )
               MapUI(
                   modifier = Modifier
                       .weight(1f)
                       .aspectRatio(1f)
                       .blend(
                           backgroundMode = BackgroundMode.FromColor(page.color),
                           alpha = .5f
                       ),
                   state = remember {
                       getMapState(
                           initialCoordinates = page.coordinates.toRelativeCoordinates(),
                           initialZoom = 15,
                           customMarkers = CustomMarkers.StaticMiniMapMarker(page),
                           disableGestures = true
                       )
                   }
               )
           }
            PlayerCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .hazeEffect(
                        state = hazeState,
                        style = HazeMaterials.thin(
                            containerColor = page.color
                        )
                    ),
                page = page
            )
        }
    }
}