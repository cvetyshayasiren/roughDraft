package cvetyshayasiren.roughdraft.domain.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import cvetyshayasiren.roughdraft.domain.utils.custom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

object SettingsState {
    private val _settings: MutableStateFlow<SettingsEntity> = MutableStateFlow(SettingsEntity())
    val settings: StateFlow<SettingsEntity> = _settings.asStateFlow()

    fun init(isDark: Boolean) = setSettings(darkTheme = isDark)

    fun setSettings(
        darkTheme: Boolean? = null
    ) {
        val old = _settings.value
        setSettings(
            SettingsEntity(
                darkTheme = darkTheme ?: old.darkTheme
            )
        )
    }

    fun setSettings(
        newSettings: SettingsEntity
    ) {
        _settings.value = newSettings
        println(_settings.value.darkTheme)
    }
}

@Composable
fun SettingsState.isDark(
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    started: SharingStarted = SharingStarted.custom()
): StateFlow<Boolean> {
    return SettingsState.settings.map {
        it.darkTheme
    }.stateIn(
        scope = scope,
        started = started,
         initialValue = isSystemInDarkTheme()
    )
}