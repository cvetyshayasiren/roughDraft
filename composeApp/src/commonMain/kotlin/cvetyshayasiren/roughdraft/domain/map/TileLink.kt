package cvetyshayasiren.roughdraft.domain.map

import cvetyshayasiren.roughdraft.data.TileLoader
import kotlinx.io.Buffer


sealed interface TileLink {
    val label: String
    val info: String
    val minZoom: Int get() = TileCoordinates.MIN_ZOOM
    val maxZoom: Int get() = TileCoordinates.MAX_ZOOM

    fun getLink(z: Int, x: Int, y: Int): String

    suspend fun loadTileBuffer(z: Int, x: Int, y: Int): Buffer {
        return TileLoader.loadTileBuffer(getLink(z, x, y))
    }

    class StandartOSM(): TileLink {
        override val label: String = "Open Street Map"
        override val info: String = "Стандартная карта Open Street Map (OSM)"
        override fun getLink(z: Int, x: Int, y: Int): String =
            "https://tile.openstreetmap.org/$z/$x/$y.png"
    }

    class CyclOSM(): TileLink {
        override val label: String = "Велодорожки"
        override val info: String = "Карта Open Street Map с велодорожками. Велодорожки отмечены синим пунктиром"

        override fun getLink(z: Int, x: Int, y: Int): String =
            "https://b.tile-cyclosm.openstreetmap.fr/cyclosm/$z/$x/$y.png"
    }

    class WaterColors(): TileLink {
        override val label: String = "Акварель"
        override val info: String = "OSM перерисованная под акварель. " +
                "Максимальное приближение 16, дальше карта будет автоматически отображаться в стандартном OSM виде"
        override val maxZoom: Int = 16

        override fun getLink(z: Int, x: Int, y: Int): String =
            if(z > 16) "https://tile.openstreetmap.org/$z/$x/$y.png"
            else "https://watercolormaps.collection.cooperhewitt.org/tile/watercolor/$z/$x/$y.jpg"
    }

    companion object {
        val TILE_LINKS_LIST: List<TileLink> = listOf(StandartOSM(), CyclOSM(), WaterColors())
    }
}