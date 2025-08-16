package cvetyshayasiren.roughdraft.domain.map

import cvetyshayasiren.roughdraft.data.TileLoader
import kotlinx.io.Buffer
import ovh.plrapps.mapcompose.ui.layout.Forced
import ovh.plrapps.mapcompose.ui.layout.MinimumScaleMode
import roughdraft.composeapp.generated.resources.Res
import kotlin.math.pow


sealed interface TileLink {
    val label: String
    val minZoom: Int get() = TileCoordinates.MIN_ZOOM
    val maxZoom: Int get() = TileCoordinates.MAX_ZOOM

    fun getLink(z: Int, x: Int, y: Int): String

    suspend fun loadTileBuffer(z: Int, x: Int, y: Int): Buffer {
        return TileLoader.loadTileBuffer(getLink(z, x, y))
    }

    class StandartOSM(): TileLink {
        override val label: String = "Open Street Map"
        override fun getLink(z: Int, x: Int, y: Int): String =
            "https://tile.openstreetmap.org/$z/$x/$y.png"
    }

    class CyclOSM(): TileLink {
        override val label: String = "Велодорожки"

        override fun getLink(z: Int, x: Int, y: Int): String =
            "https://b.tile-cyclosm.openstreetmap.fr/cyclosm/$z/$x/$y.png"
    }

    class WaterColors(): TileLink {
        override val label: String = "Акварель"
        override val maxZoom: Int = 16

        override fun getLink(z: Int, x: Int, y: Int): String =
            "https://watercolormaps.collection.cooperhewitt.org/tile/watercolor/$z/$x/$y.jpg"

        override suspend fun loadTileBuffer(z: Int, x: Int, y: Int): Buffer {
            return if(z > 16) {
                val buffer = Buffer()
                buffer.write(Res.readBytes("files/errorTile.jpg"))
                buffer
            } else super.loadTileBuffer(z, x, y)
        }
    }

    companion object {
        val TILE_LINKS_LIST: List<TileLink> = listOf(StandartOSM(), CyclOSM(), WaterColors())
    }
}