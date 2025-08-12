package cvetyshayasiren.roughdraft.ui.test

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cvetyshayasiren.roughdraft.domain.map.GeoCoordinates
import cvetyshayasiren.roughdraft.domain.map.Latitude
import cvetyshayasiren.roughdraft.domain.map.Longitude
import cvetyshayasiren.roughdraft.domain.map.TileCoordinates
import cvetyshayasiren.roughdraft.domain.map.TileLink
import cvetyshayasiren.roughdraft.domain.map.getMapState
import cvetyshayasiren.roughdraft.domain.settings.SettingsState
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import kotlinx.coroutines.launch
import ovh.plrapps.mapcompose.api.reloadTiles
import ovh.plrapps.mapcompose.api.scrollTo
import ovh.plrapps.mapcompose.api.visibleBoundingBox
import ovh.plrapps.mapcompose.ui.MapUI
import kotlin.math.pow

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MapTest() {
    val settings = SettingsState.settings.collectAsState()
    val initZoom = remember { 16 }
    val mapState = remember {
        getMapState(
            tileLink = { settings.value.tileLink },
            initialZoom = initZoom,
            initialCoordinates = GeoCoordinates(Latitude(59.946371), Longitude(30.373957)).toRelativeCoordinates()
        )
    }
    val scope = rememberCoroutineScope()

    val currentLevel = remember { mutableStateOf(initZoom.toFloat() / TileCoordinates.MAX_ZOOM) }

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

        Text("level ${currentLevel.value * TileCoordinates.MAX_ZOOM}")

        Slider(
            value = currentLevel.value,
            onValueChange = { value ->
                scope.launch {
                    currentLevel.value = value
                    val box = mapState.visibleBoundingBox()
                    mapState.scrollTo(
                        x = box.xLeft + (box.xRight - box.xLeft) / 2,
                        y = box.yBottom + (box.yTop - box.yBottom) / 2,
                        destScale = TileCoordinates.zoomLevelToScale(currentLevel.value * TileCoordinates.MAX_ZOOM.toDouble())
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