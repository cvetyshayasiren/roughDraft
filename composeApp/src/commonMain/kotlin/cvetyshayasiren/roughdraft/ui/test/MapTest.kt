package cvetyshayasiren.roughdraft.ui.test

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.io.Buffer
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.addMarker
import ovh.plrapps.mapcompose.api.reloadTiles
import ovh.plrapps.mapcompose.api.scale
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.MapUI
import ovh.plrapps.mapcompose.ui.layout.Forced
import ovh.plrapps.mapcompose.ui.state.MapState
import kotlin.math.pow

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MapTest() {

    val buttons = remember {
        listOf(
            Pair(0.58, 0.17),
            Pair(0.0, 0.0),
            Pair(1.0, 1.0),
            Pair(0.5, 0.5),
            Pair(0.2, 0.8),
            Pair(GeoCoordinates.SAINT_PETERSBURG.lon.toRelative(), GeoCoordinates.SAINT_PETERSBURG.lat.toRelative())
        )
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            buttons.forEach { (x, y) ->
                TextButton(
                    onClick = {
                        MapTestViewModel.state.addMarker(
                            "$x$y", x = x, y = y, relativeOffset = Offset(-.5f, -.5f)
                        ) {
                            Box(
                               modifier = Modifier
                                   .clip(MaterialShapes.Gem.toShape())
                                   .size(20.dp)
                                   .background(remember { randomColor() } )
                            )
                        }
                    }
                ) {
                    Text("add marker $x $y")
                }
            }
        }

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            listOf(TileLink.StandartOSM(), TileLink.CyclOSM(), TileLink.WaterColors()).forEach { tileLink ->
                TextButton(
                    onClick =  {
                        MapTestViewModel.switchLink(tileLink)
                    }
                ) {
                    Text(tileLink.label)
                }
            }
        }

        MapUI(
            modifier = Modifier
                .fillMaxSize(),
            state = MapTestViewModel.state
        )
    }
}

object MapTestViewModel {
    val client = HttpClient()

    var currentTileLink: TileLink = TileLink.WaterColors()


    val tileStreamProvider = TileStreamProvider { y, x, z ->
        println("z $z y $y x $x")

        try {
            val buffer = Buffer()
            val response: HttpResponse = client.request(currentTileLink.getLink(z, x, y))

            buffer.write(response.readRawBytes())
            buffer

        } catch (e: Exception) {
            println("e ${e.message}")
            null
        }
    }

    private const val MAX_LEVEL = TileCoordinates.MAX_ZOOM
    private const val MIN_LEVEL = TileCoordinates.MIN_ZOOM
    private val mapSize = mapSizeAtLevel(MAX_LEVEL, tileSize = 256)

    val state: MapState = MapState(levelCount = MAX_LEVEL + 1, mapSize, mapSize, workerCount = 16) {
        minimumScaleMode(Forced(1 / 2.0.pow(MAX_LEVEL - MIN_LEVEL)))
        scroll(
            x = GeoCoordinates.SAINT_PETERSBURG.lon.toRelative(),
            y = GeoCoordinates.SAINT_PETERSBURG.lat.toRelative(),
        )
    }.apply {
        addLayer(tileStreamProvider)
        scale = 0.1
        addMarker(
            id = "SPB",
            x = GeoCoordinates.SAINT_PETERSBURG.lon.toRelative(),
            y = GeoCoordinates.SAINT_PETERSBURG.lat.toRelative(),
            relativeOffset = Offset(-.5f, -.5f)
        ) {
            Text(text = "SPB", color = Color.Black)
        }
    }

    fun switchLink(tileLink: TileLink) {
        currentTileLink = tileLink
        state.reloadTiles()
    }

    private fun mapSizeAtLevel(wmtsLevel: Int, tileSize: Int): Int {
        return tileSize * 2.0.pow(wmtsLevel).toInt()
    }

}

fun randomColor(): Color = Color((0..255).random(), (0..255).random(), (0..255).random())

// https://tile.openstreetmap.org/{z}/{x}/{y}.png

//https://watercolormaps.collection.cooperhewitt.org/tile/watercolor/{z}/{x}/{y}.jpg