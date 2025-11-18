package cvetyshayasiren.roughdraft.ui.features.draftPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.github.panpf.sketch.AsyncImage
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftPageEntity
import cvetyshayasiren.roughdraft.domain.draftsInteractions.getComposeResourceUri
import cvetyshayasiren.roughdraft.domain.map.CustomMarkers
import cvetyshayasiren.roughdraft.domain.map.getMapState
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import cvetyshayasiren.roughdraft.ui.theme.basicText
import cvetyshayasiren.roughdraft.ui.utils.blend.BackgroundMode
import cvetyshayasiren.roughdraft.ui.utils.blend.blend
import cvetyshayasiren.roughdraft.ui.utils.containerHeightDp
import cvetyshayasiren.roughdraft.ui.utils.photo.PhotoCarousel
import cvetyshayasiren.roughdraft.ui.utils.wavy.WavyVerticalDivider
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
    val paddingOne = DesignStyle.multiBigPadding()
    val paddingTwo = DesignStyle.multiBigPadding(2)
    val paddingThree = DesignStyle.multiBigPadding(4)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = paddingThree, alignment = Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            PlayerCard(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                playCardModifier = Modifier
                    .padding(horizontal = paddingThree, vertical = paddingTwo)
                    .clip(DesignStyle.roundedShape)
                    .fillMaxWidth()
                    .padding(horizontal = paddingTwo, vertical = paddingOne),
                page = page
            )
            MapUI(
                modifier = Modifier
                    .weight(2f)
                    .aspectRatio(2f)
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
        Row {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = paddingTwo, end = paddingOne),
                text = page.poem,
                style = MaterialTheme.typography.basicText()
            )

            Column(
                modifier = Modifier
                    .weight(2f)
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = paddingOne, end = paddingTwo),
                    text = page.prose,
                    style = MaterialTheme.typography.basicText()
                )
                PhotoCarousel(
                    modifier = Modifier
                        .aspectRatio(2f)
                        .background(MaterialTheme.colorScheme.surfaceBright),
                    photoPaths = page.photoPaths
                )
            }
        }
    }
}