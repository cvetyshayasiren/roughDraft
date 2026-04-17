package cvetyshayasiren.roughdraft.domain.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cvetyshayasiren.roughdraft.domain.audioPlayer.PlaybackOptions
import cvetyshayasiren.roughdraft.domain.map.TileLink
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
        themeSeedColor: Color? = null,
        tileLink: TileLink? = null,
        playbackOptions: PlaybackOptions? = null
    ) {
        val old = _settings.value
        setSettings(
            SettingsEntity(
                themeMode = themeMode ?: old.themeMode,
                themeSeedColor = themeSeedColor ?: old.themeSeedColor,
                tileLink = tileLink ?: old.tileLink,
                playbackOptions = playbackOptions ?: old.playbackOptions
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
fun SettingsState.themeModeIsDark(
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    started: SharingStarted = SharingStarted.custom()
): StateFlow<Boolean> {
    return SettingsState.settings.map {
        it.themeMode.isDark()
    }.stateIn(
        scope = scope,
        started = started,
        initialValue = isSystemInDarkTheme()
    )
}

fun SettingsState.switchThemeMode() = setSettings(themeMode = settings.value.themeMode.switch())