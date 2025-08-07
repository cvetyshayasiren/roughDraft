package cvetyshayasiren.roughdraft.data

import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.statement.readRawBytes
import kotlinx.io.Buffer

object TileLoader {
    private val client = HttpClient()

    suspend fun loadTileBuffer(link: String): Buffer? {
        val buffer = Buffer()
        val tile = loadTile(link)
        tile?.let {
            buffer.write(it)
            return buffer
        }
        return null
    }

    private suspend fun loadTile(link: String): ByteArray? {
        try {
            return client.request(link).readRawBytes()

        } catch (e: Exception) {
            println("e ${e.message}")
            return null
        }
    }
}