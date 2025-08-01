package cvetyshayasiren.roughdraft.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.DarkMode
import androidx.compose.material.icons.sharp.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.materialkolor.DynamicMaterialExpressiveTheme
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamiccolor.ColorSpec
import com.materialkolor.rememberDynamicMaterialThemeState
import com.materialkolor.scheme.SchemeExpressive
import cvetyshayasiren.roughdraft.domain.settings.SettingsState
import cvetyshayasiren.roughdraft.domain.settings.themeMode

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = Tertiary,
    onTertiary = OnTertiary,
    tertiaryContainer = TertiaryContainer,
    onTertiaryContainer = OnTertiaryContainer,
    error = Error,
    onError = OnError,
    errorContainer = ErrorContainer,
    onErrorContainer = OnErrorContainer,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    outline = Outline
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    primaryContainer = PrimaryContainerDark,
    onPrimaryContainer = OnPrimaryContainerDark,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    secondaryContainer = SecondaryContainerDark,
    onSecondaryContainer = OnSecondaryContainerDark,
    tertiary = TertiaryDark,
    onTertiary = OnTertiaryDark,
    tertiaryContainer = TertiaryContainerDark,
    onTertiaryContainer = OnTertiaryContainerDark,
    error = ErrorDark,
    onError = OnErrorDark,
    errorContainer = ErrorContainerDark,
    onErrorContainer = OnErrorContainerDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    outline = OutlineDark
)

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