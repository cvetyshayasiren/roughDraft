package cvetyshayasiren.roughdraft.data

import fuel.Fuel
import fuel.get
import kotlinx.io.Buffer
import kotlinx.io.InternalIoApi

object TileLoader {

    @OptIn(InternalIoApi::class)
    suspend fun loadTileBuffer(link: String): Buffer = Fuel.get(link).source.buffer
}