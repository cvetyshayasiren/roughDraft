package cvetyshayasiren.roughdraft.ui.features.mapDraftBook

import androidx.compose.material3.Text
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftBookInteractions
import cvetyshayasiren.roughdraft.domain.map.GeoCoordinates
import cvetyshayasiren.roughdraft.domain.map.TileCoordinates
import cvetyshayasiren.roughdraft.domain.map.TileLink
import cvetyshayasiren.roughdraft.domain.settings.SettingsState
import cvetyshayasiren.roughdraft.ui.test.MapTestViewModel
import cvetyshayasiren.roughdraft.ui.test.MapTestViewModel.currentTileLink
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readRawBytes
import kotlinx.io.Buffer
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.addMarker
import ovh.plrapps.mapcompose.api.reloadTiles
import ovh.plrapps.mapcompose.api.scale
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.layout.Forced
import ovh.plrapps.mapcompose.ui.state.MapState
import kotlin.math.pow

object MapDraftBookViewModel {
    private val client = HttpClient()
    private const val MAX_LEVEL = TileCoordinates.MAX_ZOOM
    private const val MIN_LEVEL = TileCoordinates.MIN_ZOOM

    private val tileStreamProvider = TileStreamProvider { y, x, z ->
        try {
            val buffer = Buffer()
            val response: HttpResponse = MapTestViewModel.client.request(currentTileLink.getLink(z, x, y))

            buffer.write(response.readRawBytes())
            buffer

        } catch (e: Exception) {
            println("e ${e.message}")
            null
        }
    }
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
        DraftBookInteractions.draftBook.value.forEach { page ->
            addMarker(
                id = page.name,
                x = page.coordinates.lon.toRelative(),
                y = page.coordinates.lat.toRelative(),
                relativeOffset = Offset(-.5f, -.5f)
            ) {
                Text(text = page.name, color = Color.Black)
            }
        }
    }

    private fun mapSizeAtLevel(wmtsLevel: Int, tileSize: Int): Int {
        return tileSize * 2.0.pow(wmtsLevel).toInt()
    }

    fun switchLink(tileLink: TileLink) {
        SettingsState.setSettings(tileLink = tileLink)
        currentTileLink = tileLink
        state.reloadTiles()
    }
}