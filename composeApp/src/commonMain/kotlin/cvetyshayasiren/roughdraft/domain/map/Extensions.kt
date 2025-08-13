package cvetyshayasiren.roughdraft.domain.map

import androidx.lifecycle.viewModelScope
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftBookInteractions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ovh.plrapps.mapcompose.api.scrollTo
import ovh.plrapps.mapcompose.api.visibleBoundingBox
import ovh.plrapps.mapcompose.ui.state.MapState

fun MapState.setScale(
    zoom: Double = TileCoordinates.DEFAULT_ZOOM.toDouble(),
    scope: CoroutineScope = DraftBookInteractions.viewModelScope
) {
    scope.launch {
        val box = visibleBoundingBox()
        scrollTo(
            x = box.xLeft + (box.xRight - box.xLeft) / 2,
            y = box.yBottom + (box.yTop - box.yBottom) / 2,
            destScale = TileCoordinates.zoomLevelToScale(zoom)
        )
    }
}

fun MapState.setScale(
    zoom: Int = TileCoordinates.DEFAULT_ZOOM,
    scope: CoroutineScope = DraftBookInteractions.viewModelScope
) =
    setScale(zoom.toDouble(), scope)