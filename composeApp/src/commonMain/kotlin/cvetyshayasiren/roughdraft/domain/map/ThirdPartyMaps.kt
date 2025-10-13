package cvetyshayasiren.roughdraft.domain.map

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import cvetyshayasiren.roughdraft.ui.theme.smallText

sealed interface ThirdPartyMaps {
    val label: String

    @get:Composable
    val annotatedStringLabel: AnnotatedString
        get() = buildAnnotatedString { append(label) }

    fun getUrl(coordinates: GeoCoordinates): String

    @Composable
    fun getAnnotatedStringLink(coordinates: GeoCoordinates): AnnotatedString =
        buildAnnotatedString {
            withLink(LinkAnnotation.Url(getUrl(coordinates))) {
                append(annotatedStringLabel)
            }
        }

    companion object {
        val list = listOf(GoogleMaps(), YandexMaps(), GIS2())

        @Composable
        private fun getSpanStyle(color: Color) =
            MaterialTheme.typography.smallText(color = color).toSpanStyle()
    }

    class GoogleMaps(): ThirdPartyMaps {
        override val label: String = "google"

        private val blue = Color(66, 133, 244)
        private val red = Color(219, 68, 55)
        private val yellow = Color(244, 160, 0)
        private val green = Color(15, 157, 88)

        @get:Composable
        override val annotatedStringLabel: AnnotatedString
            get() = buildAnnotatedString {
                withStyle(style = getSpanStyle(blue)) { append("G") }
                withStyle(style = getSpanStyle(red)) { append("o") }
                withStyle(style = getSpanStyle(yellow)) { append("o") }
                withStyle(style = getSpanStyle(blue)) { append("g") }
                withStyle(style = getSpanStyle(green)) { append("l") }
                withStyle(style = getSpanStyle(red)) { append("e") }
            }

        override fun getUrl(coordinates: GeoCoordinates): String =
            "https://www.google.ru/maps/@${coordinates.lat.value},${coordinates.lon.value},15z"
    }

    class YandexMaps(): ThirdPartyMaps {
        override val label: String = "яндекс"

        private val orange = Color(252, 63, 29)
        private val black = Color(0, 0, 0)

        @get:Composable
        override val annotatedStringLabel: AnnotatedString
            get() = buildAnnotatedString {
                withStyle(style = getSpanStyle(orange)) { append("Я") }
                withStyle(style = getSpanStyle(black)) { append("ндекс") }
            }

        override fun getUrl(coordinates: GeoCoordinates): String =
            "https://yandex.ru/maps/?ll=${coordinates.lon.value}%2C${coordinates.lat.value}&utm_source=main_stripe_big&z=15"
    }

    class GIS2(): ThirdPartyMaps {
        override val label: String = "2ГИС"

        private val green = Color(203,232,141)
        private val orange = Color(255,185,46)

        @get:Composable
        override val annotatedStringLabel: AnnotatedString
            get() = buildAnnotatedString {
                withStyle(style = getSpanStyle(green)) { append("2") }
                withStyle(style = getSpanStyle(orange)) { append("ГИС") }
            }

        override fun getUrl(coordinates: GeoCoordinates): String =
            "https://2gis.ru/?m=${coordinates.lon.value}%2C${coordinates.lat.value}%2F15"
    }
}