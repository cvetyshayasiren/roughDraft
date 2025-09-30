package cvetyshayasiren.roughdraft.ui.features.draftPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import com.github.panpf.sketch.AsyncImage
import cvetyshayasiren.roughdraft.Config
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftPageEntity
import cvetyshayasiren.roughdraft.domain.draftsInteractions.getComposeResourceUri
import cvetyshayasiren.roughdraft.domain.map.CustomMarkers
import cvetyshayasiren.roughdraft.domain.map.ThirdPartyMaps
import cvetyshayasiren.roughdraft.domain.map.getMapState
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import cvetyshayasiren.roughdraft.ui.theme.basicText
import cvetyshayasiren.roughdraft.ui.utils.blend.BackgroundMode
import cvetyshayasiren.roughdraft.ui.utils.blend.blend
import cvetyshayasiren.roughdraft.ui.utils.photo.PhotoPager
import cvetyshayasiren.roughdraft.ui.utils.wavy.WavyHorizontalDivider
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import ovh.plrapps.mapcompose.ui.MapUI

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun CompactDraftPageView(
    page: DraftPageEntity,
    modifier: Modifier = Modifier
) {
    val hazeState = rememberHazeState()
    val paddingOne = DesignStyle.multiBigPadding(4)
    val paddingTwo = DesignStyle.multiBigPadding(8)
    val paddingThree = DesignStyle.multiBigPadding(16)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(paddingThree, alignment = Alignment.Top),
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .shadow(elevation = DesignStyle.shadowElevation),
            contentAlignment = Alignment.BottomCenter
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .hazeSource(state = hazeState),
                uri = page.iconPath.getComposeResourceUri(),
                contentDescription = "draft page image",
                contentScale = ContentScale.FillWidth
            )
            PlayerCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .hazeEffect(
                        state = hazeState,
                        style = HazeMaterials.thin(
                            containerColor = page.color
                        )
                    )
                    .padding(horizontal = paddingOne, vertical = paddingTwo),
                page = page
            )
        }

        Text(
            modifier = Modifier
                .padding(start = paddingTwo, end = paddingOne),
            text = page.poem,
            style = MaterialTheme.typography.basicText()
        )
        WavyHorizontalDivider(
            modifier = Modifier
                .padding(vertical = paddingOne)
                .fillMaxWidth()
                .height(paddingOne)
        )
        Text(
            modifier = Modifier
                .padding(start = paddingOne, end = paddingTwo),
            text = page.prose,
            style = MaterialTheme.typography.basicText()
        )

        PhotoPager(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(MaterialTheme.colorScheme.surfaceBright),
            pageSizeFraction = .6f,
            photoPaths = page.photoPaths
        )

        MapUI(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(paddingOne)
                .clip(DesignStyle.roundedShape)
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
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(space = DesignStyle.smallPadding()),
            verticalArrangement = Arrangement.spacedBy(DesignStyle.smallPadding())
        ) {
            ThirdPartyMaps.list.forEach { map ->
                Text(text = map.getLink(page.coordinates))
            }
        }

        Spacer(Modifier.height(Config.TINY_PLAYER_HEIGHT * 2))
    }
}