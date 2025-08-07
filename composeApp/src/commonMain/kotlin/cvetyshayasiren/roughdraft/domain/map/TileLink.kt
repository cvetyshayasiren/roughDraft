package cvetyshayasiren.roughdraft.domain.map


sealed interface TileLink {
    val label: String
    fun getLink(z: Int, x: Int, y: Int): String

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

        override fun getLink(z: Int, x: Int, y: Int): String =
            "https://watercolormaps.collection.cooperhewitt.org/tile/watercolor/$z/$x/$y.jpg"
    }

    companion object {
        val TILE_LINKS_LIST: List<TileLink> = listOf(StandartOSM(), CyclOSM(), WaterColors())
    }
}