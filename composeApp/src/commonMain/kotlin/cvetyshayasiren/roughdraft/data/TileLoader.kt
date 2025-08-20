package cvetyshayasiren.roughdraft.data

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.io.Buffer

object TileLoader {
    private val client = HttpClient()

    suspend fun loadTileBuffer(link: String): Buffer {
        val buffer = Buffer()
        try {
            val rawRequest = client.request(link).readRawBytes()
            buffer.write(rawRequest)
        } catch (e: Exception) {
            println("er ${e.message}")
        }
        return buffer
    }
}