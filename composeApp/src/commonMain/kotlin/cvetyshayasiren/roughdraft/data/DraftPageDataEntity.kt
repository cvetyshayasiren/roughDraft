package cvetyshayasiren.roughdraft.data

import com.github.ajalt.colormath.Color
import com.github.ajalt.colormath.extensions.android.composecolor.toComposeColor
import com.github.ajalt.colormath.parse
import cvetyshayasiren.roughdraft.Config
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftPageEntity
import cvetyshayasiren.roughdraft.domain.draftsInteractions.ImagePath
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
            prettyDate = timestampToString(),
            coordinates = GeoCoordinates(
                lat = Latitude(this.latitude),
                lon = Longitude(this.longitude)
            ),
            color = Color
                .parse(this.color)
                .toHSL()
                .copy(l = .5f)
                .toComposeColor(),
            iconPath = "files/$name/icon.${Config.IMAGE_EXTENSION}",
            photoPaths = getPhotosByName(name),
            audioUri = Res.getUri("files/$name/audio.${Config.AUDIO_EXTENSION}")
        )
    }

    private fun getPhotosByName(name: String): List<ImagePath> = buildList {
        repeat(numberOfPhotos) { index ->
            add("files/$name/$index.${Config.IMAGE_EXTENSION}")
        }
    }

    @OptIn(ExperimentalTime::class, FormatStringsInDatetimeFormats::class)
    private fun timestampToString(): String {
        val instant: Instant = Instant.fromEpochMilliseconds(timestamp * 1000)
        val localDateTime: LocalDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        val formattedString = localDateTime.format(LocalDateTime.Format { byUnicodePattern("dd.MM.yyyy HH:mm") })
        return formattedString
    }
 }
