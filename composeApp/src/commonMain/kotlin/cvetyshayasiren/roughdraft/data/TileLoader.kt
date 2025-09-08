package cvetyshayasiren.roughdraft.data

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.readRawBytes
import io.ktor.client.statement.request
import io.ktor.utils.io.InternalAPI
import kotlinx.io.Buffer
import kotlinx.io.InternalIoApi

object TileLoader {
    private val client = HttpClient()

    @OptIn(InternalIoApi::class, InternalAPI::class)
    suspend fun loadTileBuffer(link: String): Buffer {
        val buffer = Buffer()
        try {
           buffer.write(client.get(link).readRawBytes())
        } catch (e: Exception) {
            println(e)
        }
        return buffer
    }
}