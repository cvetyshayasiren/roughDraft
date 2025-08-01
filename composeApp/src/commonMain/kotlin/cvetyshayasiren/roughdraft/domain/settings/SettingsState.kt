package cvetyshayasiren.roughdraft.domain.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cvetyshayasiren.roughdraft.domain.utils.custom
import cvetyshayasiren.roughdraft.ui.theme.ThemeMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

object SettingsState {
    private val _settings: MutableStateFlow<SettingsEntity> = MutableStateFlow(SettingsEntity())
    val settings: StateFlow<SettingsEntity> = _settings.asStateFlow()

    fun init(isDark: Boolean) = setSettings(themeMode = if(isDark) ThemeMode.DARK else ThemeMode.LIGHT)

    fun setSettings(
        themeMode: ThemeMode? = null,
        themeSeedColor: Color? = null
    ) {
        val old = _settings.value
        setSettings(
            SettingsEntity(
                themeMode = themeMode ?: old.themeMode,
                themeSeedColor = themeSeedColor ?: old.themeSeedColor
            )
        )
    }

    fun setSettings(
        newSettings: SettingsEntity
    ) {
        _settings.value = newSettings
    }
}

@Composable
fun SettingsState.themeMode(
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    started: SharingStarted = SharingStarted.custom()
): StateFlow<ThemeMode> {
    return SettingsState.settings.map {
        it.themeMode
    }.stateIn(
        scope = scope,
        started = started,
        initialValue = if(isSystemInDarkTheme()) ThemeMode.DARK else ThemeMode.LIGHT
    )
}