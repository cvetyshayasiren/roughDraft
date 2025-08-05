package cvetyshayasiren.roughdraft.domain.draftsInteractions

import androidx.compose.ui.graphics.Color
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
    val prettyDate: String = "",
    @Contextual
    val coordinates: GeoCoordinates = GeoCoordinates.SAINT_PETERSBURG,
    @Contextual
    val color: Color = Color.Unspecified,
    val iconUri: String = "",
    val photoUris: List<String> = listOf(),
    val audioUri: String = ""
)