package cvetyshayasiren.roughdraft.domain.audioPlayer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AudioPlayer {
    private val _state: MutableStateFlow<AudioPlayerState> = MutableStateFlow(AudioPlayerState(duration = 100.0))
    actual val state: StateFlow<AudioPlayerState> = _state.asStateFlow()
    private var fakeTime: Double = 0.0

    actual fun prepare(uri: String) { }

    actual fun play(
        coroutineScope: CoroutineScope,
        delay: Long
    ) {
        _state.value = _state.value.copy(isPlaying = true, firstInteractionDone = true)
        coroutineScope.launch {
            while(_state.value.isPlaying) {
                fakeTime += 0.01
                if(fakeTime >= _state.value.duration) { fakeTime = 0.0 }
                _state.value = _state.value.copy(currentTime = fakeTime)
                delay(delay)
            }
        }
    }

    actual fun pause() {
        _state.value = _state.value.copy(isPlaying = false)
    }

    actual fun stop() {
        _state.value = _state.value.copy(isPlaying = false, currentTime = 0.0)
    }

    actual fun setProgress(value: Double) {
        _state.value = _state.value.copy(currentTime = value * _state.value.duration)
    }

    actual fun setVolume(value: Double) {
        _state.value = _state.value.copy(volume = value)
    }

    actual fun close() {
        stop()
    }
}