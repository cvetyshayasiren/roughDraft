package cvetyshayasiren.roughdraft.ui.features.draftBook

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cvetyshayasiren.roughdraft.Config
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftPageEntity
import cvetyshayasiren.roughdraft.domain.map.CustomMarkers
import cvetyshayasiren.roughdraft.domain.map.getMapState
import ovh.plrapps.mapcompose.ui.MapUI

@Composable
fun StaticMiniMapView(
    modifier: Modifier = Modifier,
    page: DraftPageEntity
) {
    val mapState = remember {
        getMapState(
            initialCoordinates = page.coordinates.toRelativeCoordinates(),
            initialZoom = Config.MINI_MAP_LEVEL,
            disableGestures = true,
            customMarkers = CustomMarkers.StaticMiniMapMarker(listOf(page))
        )
    }
    MapUI(
        modifier = modifier,
        state = mapState
    )
}