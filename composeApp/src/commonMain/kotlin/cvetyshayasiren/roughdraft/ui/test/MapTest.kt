package cvetyshayasiren.roughdraft.ui.test

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftPageEntity
import cvetyshayasiren.roughdraft.domain.map.GeoCoordinates
import cvetyshayasiren.roughdraft.domain.map.Latitude
import cvetyshayasiren.roughdraft.domain.map.Longitude
import cvetyshayasiren.roughdraft.domain.map.TileCoordinates
import cvetyshayasiren.roughdraft.domain.map.TileLink
import cvetyshayasiren.roughdraft.domain.map.getMapState
import cvetyshayasiren.roughdraft.domain.settings.SettingsState
import cvetyshayasiren.roughdraft.ui.utils.wavy.WavyHorizontalDivider
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.io.Buffer
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.addMarker
import ovh.plrapps.mapcompose.api.reloadTiles
import ovh.plrapps.mapcompose.api.scale
import ovh.plrapps.mapcompose.api.scrollTo
import ovh.plrapps.mapcompose.api.visibleBoundingBox
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.MapUI
import ovh.plrapps.mapcompose.ui.layout.Forced
import ovh.plrapps.mapcompose.ui.state.MapState
import kotlin.math.pow

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MapTest() {
    val settings = SettingsState.settings.collectAsState()
    val mapState = remember { getMapState(tileLink = { TileLink.StandartOSM() }, initialZoom = 16) }
    val scope = rememberCoroutineScope()

    val currentScale = remember { mutableStateOf(TileCoordinates.scaleToRelative(mapState.scale)) }

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            listOf(TileLink.StandartOSM(), TileLink.CyclOSM(), TileLink.WaterColors()).forEach { tileLink ->
                TextButton(
                    onClick =  {
                        SettingsState.setSettings(tileLink = tileLink)
                        mapState.reloadTiles()
                    }
                ) {
                    Text(tileLink.label)
                }
            }
        }

        Text("relative ${currentScale.value} scale ${TileCoordinates.relativeToScale(currentScale.value.toFloat())}")
        Slider(
            value = currentScale.value.toFloat(),
            onValueChange = {
                scope.launch {
                    currentScale.value = it
                    val scale = TileCoordinates.relativeToScale(value = it)
                    mapState.scrollTo(
                        x = mapState.visibleBoundingBox().xLeft + (mapState.visibleBoundingBox().xRight - mapState.visibleBoundingBox().xLeft) / 2,
                        y = mapState.visibleBoundingBox().yBottom + (mapState.visibleBoundingBox().yTop - mapState.visibleBoundingBox().yBottom) / 2,
                        destScale = scale
                    )
                }
            }
        )

        MapUI(
            modifier = Modifier
                .fillMaxWidth().height(600.dp),
            state = mapState
        )
    }
}

fun randomColor(): Color = Color((0..255).random(), (0..255).random(), (0..255).random())