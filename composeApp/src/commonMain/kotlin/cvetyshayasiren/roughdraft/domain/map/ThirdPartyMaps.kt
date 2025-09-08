package cvetyshayasiren.roughdraft.domain.map

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink

sealed interface ThirdPartyMaps {
    val label: String
    fun getLink(coordinates: GeoCoordinates): AnnotatedString

    companion object {
        val list = listOf(GoogleMaps(), YandexMaps(), GIS2())
    }

    class GoogleMaps(): ThirdPartyMaps {
        override val label: String = "google"

        override fun getLink(coordinates: GeoCoordinates): AnnotatedString = buildAnnotatedString {
            withLink(
                LinkAnnotation.Url(
                    "https://www.google.ru/maps/@${coordinates.lat.value},${coordinates.lon.value},15z",
                )
            ) {
                append(label)
            }
        }
    }

    class YandexMaps(): ThirdPartyMaps {
        override val label: String = "яндекс"

        override fun getLink(coordinates: GeoCoordinates): AnnotatedString = buildAnnotatedString {
            withLink(
                LinkAnnotation.Url(
                    "https://yandex.ru/maps/?ll=${coordinates.lon.value}%2C${coordinates.lat.value}&utm_source=main_stripe_big&z=15",
                )
            ) {
                append(label)
            }
        }
    }

    class GIS2(): ThirdPartyMaps {
        override val label: String = "2ГИС"

        override fun getLink(coordinates: GeoCoordinates): AnnotatedString = buildAnnotatedString {
            withLink(
                LinkAnnotation.Url(
                    "https://2gis.ru/?m=${coordinates.lon.value}%2C${coordinates.lat.value}%2F15",
                )
            ) {
                append(label)
            }
        }
    }
}