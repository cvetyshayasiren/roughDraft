package cvetyshayasiren.roughdraft.data

import cvetyshayasiren.roughdraft.Config
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftOptionsEntity
import cvetyshayasiren.roughdraft.domain.map.GeoCoordinates
import cvetyshayasiren.roughdraft.domain.map.Latitude
import cvetyshayasiren.roughdraft.domain.map.Longitude
import kotlinx.serialization.Serializable
import roughdraft.composeapp.generated.resources.Res

@Serializable
data class DraftOptionsDataEntity(
    val poem: String = "",
    val prose: String = "",
    val numberOfPhotos: Int = 0,
    val timestamp: Long = 0,
    val longitude: Double = 0.0,
    val latitude: Double = 0.0
) {
    fun toDraftOptionsEntity(name: String): DraftOptionsEntity {
        return DraftOptionsEntity(
            name = name,
            poem = this.poem,
            prose = this.prose,
            numberOfPhotos = this.numberOfPhotos,
            timestamp = this.timestamp,
            coordinates = GeoCoordinates(
                lat = Latitude(this.latitude),
                lon = Longitude(this.longitude)
            ),
            iconUri = Res.getUri("files/$name/icon.${Config.IMAGE_EXTENSION}"),
            photoUris = getPhotosByName(name),
            audioUri = Res.getUri("files/$name/audio.${Config.AUDIO_EXTENSION}")
        )
    }

    private fun getPhotosByName(name: String): List<String> = buildList {
        repeat(numberOfPhotos) { index ->
            add(Res.getUri("files/$name/$index.${Config.IMAGE_EXTENSION}"))
        }
    }
 }
