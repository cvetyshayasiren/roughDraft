package cvetyshayasiren.roughdraft.ui.features.draftPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
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
import cvetyshayasiren.roughdraft.ui.utils.photo.PhotoPager
import cvetyshayasiren.roughdraft.ui.utils.wavy.WavyCrestStructure
import cvetyshayasiren.roughdraft.ui.utils.wavy.WavyHorizontalDivider
import cvetyshayasiren.roughdraft.ui.utils.wavy.WavyVerticalDivider
import cvetyshayasiren.roughdraft.ui.utils.wavy.wavy
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
    val wavyHeight = remember { mutableStateOf(0.dp) }
    val hazeState = rememberHazeState()
    val paddingOne = DesignStyle.multiBigPadding()
    val paddingTwo = DesignStyle.multiBigPadding(2)
    val paddingThree = DesignStyle.multiBigPadding(4)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = paddingThree, alignment = Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
           Row(
               modifier = Modifier
                   .hazeSource(state = hazeState)
           ) {
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
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = paddingThree, vertical = paddingTwo)
                    .clip(DesignStyle.roundedShape)
                    .fillMaxWidth()
                    .hazeEffect(
                        state = hazeState,
                        style = HazeMaterials.thin(
                            containerColor = page.color
                        )
                    )
                    .padding(horizontal = paddingTwo, vertical = paddingOne),
                page = page
            )
        }
        Row(
            modifier = Modifier.containerHeightDp(wavyHeight)
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = paddingTwo, end = paddingOne),
                text = page.poem,
                style = MaterialTheme.typography.basicText()
            )
            WavyVerticalDivider(
                modifier = Modifier
                    .height(paddingThree * 5)
                    .width(paddingOne)
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = paddingOne, end = paddingTwo),
                text = page.prose,
                style = MaterialTheme.typography.basicText()
            )
        }

        PhotoPager(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(MaterialTheme.colorScheme.surfaceBright),
            pageSizeFraction = .4f,
            photoPaths = page.photoPaths
        )
    }
}