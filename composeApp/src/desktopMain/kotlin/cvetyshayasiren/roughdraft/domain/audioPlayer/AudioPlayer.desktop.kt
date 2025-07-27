package cvetyshayasiren.roughdraft.domain.audioPlayer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AudioPlayer {
    private val _state: MutableStateFlow<AudioPlayerState> = MutableStateFlow(AudioPlayerState())
    actual val state: StateFlow<AudioPlayerState> = _state.asStateFlow()
    private var fakeProgress: Float = 0f

    actual fun prepare(uri: String) { }

    actual fun play(
        coroutineScope: CoroutineScope,
        delay: Long
    ) {
        _state.value = _state.value.copy(isPlaying = true)
        coroutineScope.launch {
            while(_state.value.isPlaying) {
                fakeProgress += .01f
                if(fakeProgress >= 1f) { fakeProgress = 0f }
                _state.value = _state.value.copy(progress = fakeProgress)
                delay(delay)
            }
        }
    }

    actual fun pause() {
        _state.value = _state.value.copy(isPlaying = false)
    }

    actual fun stop() {
        _state.value = _state.value.copy(isPlaying = false, progress = 0f)
    }

    actual fun setProgress(value: Float) {
        _state.value = _state.value.copy(progress = value)
    }

    actual fun setVolume(value: Float) {
        _state.value = _state.value.copy(volume = value)
    }
}