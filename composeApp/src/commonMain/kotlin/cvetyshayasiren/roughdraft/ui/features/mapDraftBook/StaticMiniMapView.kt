package cvetyshayasiren.roughdraft.ui.features.mapDraftBook

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftPageEntity
import cvetyshayasiren.roughdraft.domain.map.getMapState
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.ui.MapUI

@Composable
fun StaticMiniMapView(
    modifier: Modifier = Modifier,
    page: DraftPageEntity
) {
    val mapState = remember {
        getMapState(
            disableGestures = true,
            markers = listOf(page)
        )
    }
    MapUI(
        modifier = modifier,
        state = mapState
    )
}