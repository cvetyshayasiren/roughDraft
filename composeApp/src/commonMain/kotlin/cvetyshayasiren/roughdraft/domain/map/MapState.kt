package cvetyshayasiren.roughdraft.domain.map

import androidx.compose.material3.Text
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import cvetyshayasiren.roughdraft.data.TileLoader
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftBookInteractions
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftPageEntity
import cvetyshayasiren.roughdraft.domain.settings.SettingsState
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.addMarker
import ovh.plrapps.mapcompose.api.disableGestures
import ovh.plrapps.mapcompose.api.scale
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.layout.Forced
import ovh.plrapps.mapcompose.ui.state.MapState
import kotlin.math.pow

fun getMapState(
    initialCoordinates: GeoCoordinates = GeoCoordinates.SAINT_PETERSBURG,
    initialScale: Double = 0.1,
    tileLink: () -> TileLink = { TileLink.WaterColors() },
    markers: List<DraftPageEntity> = DraftBookInteractions.draftBook.value,
    disableGestures: Boolean = false,
    onApply: () -> Unit = { }
): MapState {
    return MapState(
        levelCount = TileCoordinates.MAX_ZOOM + 1,
        fullWidth = TileCoordinates.defaultMapSize,
        fullHeight = TileCoordinates.defaultMapSize,
        workerCount = 16) {
        minimumScaleMode(
            minimumScaleMode = Forced(
                scale = 1 / 2.0.pow(
                    n = TileCoordinates.MAX_ZOOM - TileCoordinates.MIN_ZOOM
                )
            )
        )
        scroll(
            x = initialCoordinates.lon.toRelative(),
            y = initialCoordinates.lat.toRelative(),
        )
    }.apply {
        if(disableGestures) { disableGestures() }
        addLayer(
            tileStreamProvider = { y, x, z ->
                TileLoader.loadTileBuffer(tileLink().getLink(z, x, y))
            }
        )
        scale = initialScale
        markers.forEach { page ->
            addMarker(
                id = page.name,
                x = page.coordinates.lon.toRelative(),
                y = page.coordinates.lat.toRelative(),
                relativeOffset = Offset(-.5f, -.5f)
            ) {
                Text(text = page.name, color = Color.Black)
            }
        }
        onApply()
    }
}