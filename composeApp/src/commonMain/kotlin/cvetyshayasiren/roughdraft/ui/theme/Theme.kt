package cvetyshayasiren.roughdraft.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.DarkMode
import androidx.compose.material.icons.sharp.LightMode
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MotionScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.vector.ImageVector
import com.materialkolor.DynamicMaterialExpressiveTheme
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamiccolor.ColorSpec
import com.materialkolor.rememberDynamicMaterialThemeState
import cvetyshayasiren.roughdraft.domain.settings.SettingsState

enum class ThemeMode(
    val label: String,
    val icon: ImageVector
) {
    DARK(label = "тёмная", icon = Icons.Sharp.DarkMode),
    LIGHT(label = "светлая", icon = Icons.Sharp.LightMode);

    fun isDark(): Boolean = this == DARK
    fun switch(): ThemeMode = if(isDark()) LIGHT else DARK
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RoughDraftExpressiveTheme(
    content: @Composable () -> Unit
) {
    val settings = SettingsState.settings.collectAsState()

    val dynamicThemeState = rememberDynamicMaterialThemeState(
        isDark = settings.value.themeMode.isDark(),
        style = PaletteStyle.Vibrant,
        specVersion = ColorSpec.SpecVersion.SPEC_2025,
        seedColor = settings.value.themeSeedColor,
    )

    DynamicMaterialExpressiveTheme(
        state = dynamicThemeState,
        motionScheme = MotionScheme.expressive(),
        animate = true,
        content = content,
    )
}