package cvetyshayasiren.roughdraft.domain.map

import kotlin.jvm.JvmInline
import kotlin.math.*

//Широта
@JvmInline
value class Latitude(val value: Double) {
    init {
        require(value >= -90 && value <= 90) {
            "The latitude should be in the range from -90 to 90, and now $value" }
    }

    fun toRadian(): Double = value.toRadian()

    fun toTileNumber(zoom: Int = TileCoordinates.DEFAULT_ZOOM): Int = tileOffset(zoom).toInt()

    fun toRelative(
        maxZoom: Int = TileCoordinates.MAX_ZOOM,
        tileSize: Int = TileCoordinates.DEFAULT_TILE_SIZE
    ): Double = (tileOffset(maxZoom) * tileSize / TileCoordinates.mapSizeAtZoom(maxZoom, tileSize)).coerceIn(0.0, 1.0)

    private fun tileOffset(zoom: Int = TileCoordinates.DEFAULT_ZOOM): Double {
        val n = 2.0.pow(zoom)
        val latRad = toRadian()
        val secLat = 1 / cos(latRad)
        return (n * (1 - (ln(tan(latRad) + secLat) / PI)) / 2)
    }

    companion object {
        const val MIN = -90.0
        const val MAX = 90.0
    }
}

//Долгота
@JvmInline
value class Longitude(val value: Double) {
    init {
        require(value >= -180 && value <= 180) {
            "The longitude should be in the range from -180 to 180, and now $value" }
    }

    fun toRadian(): Double = value.toRadian()

    fun toTileNumber(zoom: Int = TileCoordinates.DEFAULT_ZOOM): Int = tileOffset(zoom).toInt()

    fun toRelative(
        maxZoom: Int = TileCoordinates.MAX_ZOOM,
        tileSize: Int = TileCoordinates.DEFAULT_TILE_SIZE
    ): Double = (tileOffset(maxZoom) * tileSize / TileCoordinates.mapSizeAtZoom(maxZoom, tileSize)).coerceIn(0.0, 1.0)

    private fun tileOffset(zoom: Int = TileCoordinates.DEFAULT_ZOOM): Double {
        val n = 2.0.pow(zoom)
        return (n * ((value + 180.0) / 360.0))
    }

    companion object {
        const val MIN = -180.0
        const val MAX = 180.0
    }
}

data class GeoCoordinates(
    val lat: Latitude = SAINT_PETERSBURG.lat,
    val lon: Longitude = SAINT_PETERSBURG.lon
) {
    fun toTileCoordinates(zoom: Int = TileCoordinates.DEFAULT_ZOOM): TileCoordinates =
        TileCoordinates(lon.toTileNumber(zoom), lat.toTileNumber(zoom))

    companion object {
        val SAINT_PETERSBURG = GeoCoordinates(
            lat = Latitude(59.9390254087881),
            lon = Longitude(30.31576436620898)
        )
    }
}

data class TileCoordinates(
    val x: Int = SAINT_PETERSBURG.x,
    val y: Int = SAINT_PETERSBURG.y,
    val z: Int = SAINT_PETERSBURG.z
) {
    fun toGeoCoordinates(): GeoCoordinates {
        val n = 2.0.pow(z)
        val lon = x / n * 360.0 - 180.0
        val latRadian = atan(sinh(PI * (1 - 2 * y / n)))
        val lat = latRadian * 180.0 / PI

        return GeoCoordinates(lat = Latitude(lat), lon = Longitude(lon))
    }

    companion object {
        const val DEFAULT_TILE_SIZE = 256
        const val MIN_ZOOM = 1
        const val MAX_ZOOM = 19
        const val DEFAULT_ZOOM = 12
        val SAINT_PETERSBURG by lazy { GeoCoordinates.SAINT_PETERSBURG.toTileCoordinates(DEFAULT_ZOOM) }

        fun mapSizeAtZoom(
            maxZoom: Int = MAX_ZOOM,
            tileSize: Int = DEFAULT_TILE_SIZE
        ): Int {
            return tileSize * 2.0.pow(maxZoom).toInt()
        }
    }
}