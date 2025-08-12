package cvetyshayasiren.roughdraft.domain.draftsInteractions

import androidx.compose.animation.animateColorAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.github.ajalt.colormath.extensions.android.composecolor.toColormathColor
import com.github.ajalt.colormath.extensions.android.composecolor.toComposeColor
import com.materialkolor.ktx.darken
import com.materialkolor.ktx.lighten
import cvetyshayasiren.roughdraft.domain.map.GeoCoordinates
import cvetyshayasiren.roughdraft.domain.settings.SettingsState
import cvetyshayasiren.roughdraft.domain.settings.themeModeIsDark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
) {
    @Composable
    fun getBrush(scope: CoroutineScope = CoroutineScope(Dispatchers.Default)): Brush {
        val isDark = SettingsState.themeModeIsDark(scope = scope).collectAsState()
        val colorFrom by animateColorAsState(
            if(isDark.value) color.darken(1.4f) else color.lighten(2.0f)
        )
        val colorTo by animateColorAsState(
            if(isDark.value) color.darken(2.0f) else color.lighten(1.4f)
        )
        return Brush.horizontalGradient(listOf(colorFrom, colorTo))
    }

    @Composable
    fun getOnColor(scope: CoroutineScope = CoroutineScope(Dispatchers.Default)): Color {
        val isDark = SettingsState.themeModeIsDark(scope = scope).collectAsState()
        val onPageColor by animateColorAsState(
            color
                .toColormathColor()
                .toHSL()
                .let { hsl ->
                    if(isDark.value) hsl.copy(l = .8f) else hsl.copy(l = .2f)
                }
                .toComposeColor()
        )
        return onPageColor
    }
}