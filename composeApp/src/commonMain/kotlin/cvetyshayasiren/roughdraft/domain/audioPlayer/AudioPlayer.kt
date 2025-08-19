package cvetyshayasiren.roughdraft.domain.audioPlayer

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftBookInteractions
import cvetyshayasiren.roughdraft.domain.settings.SettingsState
import cvetyshayasiren.roughdraft.domain.utils.custom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class AudioPlayer() {
    val state: StateFlow<AudioPlayerState>

    fun prepare(uri: String)

    fun play(
        coroutineScope: CoroutineScope = DraftBookInteractions.viewModelScope,
        delay: Long = 100
    )

    fun pause()

    fun stop()

    fun setProgress(value: Double)

    fun setVolume(value: Double)

    fun close()
}

fun AudioPlayer.songIsOver(
    scope: CoroutineScope = DraftBookInteractions.viewModelScope,
    started: SharingStarted = SharingStarted.custom()
): StateFlow<Boolean> = state
    .map { it.isOver() }
    .stateIn(
        scope = scope,
        started = started,
        initialValue = false
    )