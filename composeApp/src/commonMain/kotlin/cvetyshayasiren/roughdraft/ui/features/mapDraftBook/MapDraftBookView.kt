package cvetyshayasiren.roughdraft.ui.features.mapDraftBook

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import cvetyshayasiren.roughdraft.domain.map.TileLink
import cvetyshayasiren.roughdraft.domain.map.getMapState
import kotlinx.coroutines.launch
import ovh.plrapps.mapcompose.api.reloadTiles
import ovh.plrapps.mapcompose.api.scale
import ovh.plrapps.mapcompose.api.scrollTo
import ovh.plrapps.mapcompose.api.visibleBoundingBox
import ovh.plrapps.mapcompose.ui.MapUI
import ovh.plrapps.mapcompose.ui.state.MapState

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalComposeUiApi::class)
@Composable
fun MapDraftBookView(
    modifier: Modifier = Modifier,
) {
    val mapState = remember { getMapState() }
    val scope = rememberCoroutineScope()
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        MapUI(
            modifier = Modifier
                .fillMaxSize(),
            state = mapState
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
        ) {
            Button(
                onClick = {
                    scope.launch {
                        mapState.scaleIn(mapState.scale + 0.1)
                    }

                }
            ) {
                Text("+")
            }
            Button(
                onClick = {
                    scope.launch {
                        mapState.scaleIn(mapState.scale - 0.1)
                    }
                }
            ) {
                Text("-")
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .horizontalScroll(rememberScrollState())
        ) {
            TileLink.TILE_LINKS_LIST.forEach { tileLink ->
                val mapState = remember { getMapState(tileLink = { tileLink }, disableGestures = true) }
                MapUI(
                    modifier = modifier.size(200.dp),
                    state = mapState
                )
            }
        }
    }
}

suspend fun MapState.scaleIn(scale: Double) {
    this.scrollTo(
        x = this.visibleBoundingBox().xLeft,
        y = this.visibleBoundingBox().yTop,
        destScale = scale
    )
}