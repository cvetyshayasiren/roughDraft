package cvetyshayasiren.roughdraft.domain.draftsInteractions

import cvetyshayasiren.roughdraft.domain.map.GeoCoordinates
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class DraftPageEntity(
    val name: String = "",
    val poem: String = "",
    val prose: String = "",
    val numberOfPhotos: Int = 0,
    val timestamp: Long = 0,
    @Contextual
    val coordinates: GeoCoordinates = GeoCoordinates.SAINT_PETERSBURG,
    val iconUri: String = "",
    val photoUris: List<String> = listOf(),
    val audioUri: String = ""
)