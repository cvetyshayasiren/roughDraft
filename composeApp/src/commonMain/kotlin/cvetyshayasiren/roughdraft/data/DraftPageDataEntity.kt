package cvetyshayasiren.roughdraft.data

import androidx.compose.ui.graphics.Color
import cvetyshayasiren.roughdraft.Config
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftPageEntity
import cvetyshayasiren.roughdraft.domain.map.GeoCoordinates
import cvetyshayasiren.roughdraft.domain.map.Latitude
import cvetyshayasiren.roughdraft.domain.map.Longitude
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import roughdraft.composeapp.generated.resources.Res
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
data class DraftPageDataEntity(
    val poem: String = "",
    val prose: String = "",
    val numberOfPhotos: Int = 0,
    val timestamp: Long = 0,
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,
    val color: String
) {
    fun toDraftPageEntity(name: String): DraftPageEntity {
        return DraftPageEntity(
            name = name,
            poem = this.poem,
            prose = this.prose,
            numberOfPhotos = this.numberOfPhotos,
            timestamp = this.timestamp,
            prettyDate = timestampToString(this.timestamp),
            coordinates = GeoCoordinates(
                lat = Latitude(this.latitude),
                lon = Longitude(this.longitude)
            ),
            color = Color(this.color.drop(1).toInt(16)).copy(alpha = 1f),
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

    @OptIn(ExperimentalTime::class, FormatStringsInDatetimeFormats::class)
    private fun timestampToString(timestamp: Long): String {
        val instant: Instant = Instant.fromEpochMilliseconds(timestamp)
        val localDateTime: LocalDateTime = instant.toLocalDateTime(TimeZone.UTC)
        val formattedString = localDateTime.format(LocalDateTime.Format { byUnicodePattern("MM.dd.yyyy HH:mm") })
        return formattedString
    }
 }
