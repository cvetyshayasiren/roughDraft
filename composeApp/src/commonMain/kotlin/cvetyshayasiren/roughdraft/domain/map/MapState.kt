package cvetyshayasiren.roughdraft.domain.map

import cvetyshayasiren.roughdraft.data.TileLoader
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.disableGestures
import ovh.plrapps.mapcompose.api.scale
import ovh.plrapps.mapcompose.ui.layout.Forced
import ovh.plrapps.mapcompose.ui.state.MapState
import kotlin.math.pow

fun getMapState(
    initialCoordinates: RelativeCoordinates = RelativeCoordinates.SAINT_PETERSBURG,
    initialZoom: Int = TileCoordinates.DEFAULT_ZOOM,
    tileLink: () -> TileLink = { TileLink.WaterColors() },
    disableGestures: Boolean = false,
    customMarkers: CustomMarkers = CustomMarkers.EnhancedMapMarker(),
    onApply: MapState.() -> Unit = { }
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
        customMarkers.behaviour(this)
        onApply()
    }
}