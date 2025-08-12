package cvetyshayasiren.roughdraft.domain.map

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil3.CoilImage
import cvetyshayasiren.roughdraft.data.TileLoader
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftBookInteractions
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftPageEntity
import cvetyshayasiren.roughdraft.ui.navigation.coloredBorder
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.addMarker
import ovh.plrapps.mapcompose.api.disableGestures
import ovh.plrapps.mapcompose.api.scale
import ovh.plrapps.mapcompose.api.scrollTo
import ovh.plrapps.mapcompose.api.visibleBoundingBox
import ovh.plrapps.mapcompose.ui.layout.Forced
import ovh.plrapps.mapcompose.ui.state.MapState
import kotlin.math.pow

fun getMapState(
    initialCoordinates: RelativeCoordinates = RelativeCoordinates.SAINT_PETERSBURG,
    initialZoom: Int = TileCoordinates.DEFAULT_ZOOM,
    tileLink: () -> TileLink = { TileLink.WaterColors() },
    markers: List<DraftPageEntity> = DraftBookInteractions.draftBook.value,
    disableGestures: Boolean = false
): MapState {
    return MapState(
        levelCount = TileCoordinates.MAX_ZOOM + 1,
        fullWidth = TileCoordinates.worldMapSize,
        fullHeight = TileCoordinates.worldMapSize,
        workerCount = 16
    ) {
        minimumScaleMode(
            minimumScaleMode = Forced(
                scale = 1 / 2.0.pow(
                    n = TileCoordinates.MAX_ZOOM - TileCoordinates.MIN_ZOOM
                )
            )
        )
        scroll(x = initialCoordinates.x, y = initialCoordinates.y)
    }.apply {
        if(disableGestures) { disableGestures() }
        addLayer(
            tileStreamProvider = { y, x, z ->
                TileLoader.loadTileBuffer(tileLink().getLink(z, x, y))
            }
        )
        scale = TileCoordinates.zoomLevelToScale(initialZoom)
        markers.forEach { page ->
            addMarker(
                id = page.name,
                x = page.coordinates.lon.toRelative(),
                y = page.coordinates.lat.toRelative(),
                relativeOffset = Offset(-.5f, -.5f)
            ) {
                CoilImage(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(shape = DesignStyle.markerShape)
                        .shadow(elevation = DesignStyle.shadowElevation),
                    imageModel = { page.iconUri }
                )
                Text(text = page.name, color = Color.Black)
            }
        }
    }
}