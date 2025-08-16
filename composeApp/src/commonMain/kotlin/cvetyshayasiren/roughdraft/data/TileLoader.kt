package cvetyshayasiren.roughdraft.data

import io.ktor.client.*
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.io.Buffer
import roughdraft.composeapp.generated.resources.Res

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