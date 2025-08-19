package cvetyshayasiren.roughdraft.domain.audioPlayer

import kotlinx.browser.document
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.dom.appendElement
import org.w3c.dom.HTMLAudioElement
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AudioPlayer {
    @OptIn(ExperimentalUuidApi::class)
    private val htmlId = Uuid.random().toString()

    private val _state: MutableStateFlow<AudioPlayerState> = MutableStateFlow(AudioPlayerState())

    actual val state: StateFlow<AudioPlayerState> = _state.asStateFlow()

    actual fun prepare(uri: String) {
        getPlayerElement()?.apply { pause(); close() }
        document.body?.appendElement("audio") {
            this as HTMLAudioElement
            this.id = htmlId
            this.src = uri
            this.volume = _state.value.volume
        }
        getPlayerElement()?.apply {
            setState(isReady = true, currentTime = currentTime, duration = duration, isPlaying = false)
        }

    }

    actual fun play(
        coroutineScope: CoroutineScope,
        delay: Long
    ) {
        getPlayerElement()?.apply {
            play()
            setState(isPlaying = true, firstInteractionDone = true)

            coroutineScope.launch {
                while(_state.value.isPlaying) {
                    getPlayerElement()?.apply {
                        setState(currentTime = currentTime, duration = duration)
                    }
                    delay(delay)
                }
            }
        }

    }

    actual fun pause() {
        getPlayerElement()?.pause()
        setState(isPlaying = false)
    }

    actual fun stop() {
        getPlayerElement()?.apply {
            pause()
            currentTime = 0.0
            setState(isPlaying = false, currentTime = currentTime)
        }
    }

    actual fun setProgress(value: Double) {
        getPlayerElement()?.apply {
            currentTime = value * duration
            setState(currentTime = currentTime)
        }
    }

    actual fun setVolume(value: Double) {
        getPlayerElement()?.apply {
            setState(volume = value)
        }
    }

    actual fun close() {
        getPlayerElement()?.apply {
            remove()
            setState(AudioPlayerState())
        }
    }

    private fun setState(
        isReady: Boolean? = null,
        isPlaying: Boolean? = null,
        volume: Double? = null,
        currentTime: Double? = null,
        duration: Double? = null,
        firstInteractionDone: Boolean? = null,
    ) {
        val old = _state.value
        setState(
            AudioPlayerState(
                isReady = isReady ?: old.isReady,
                isPlaying = isPlaying ?: old.isPlaying,
                volume = volume ?: old.volume,
                currentTime = currentTime ?: old.currentTime,
                duration = duration ?: old.duration,
                firstInteractionDone = firstInteractionDone ?: old.firstInteractionDone
            )
        )
    }

    private fun setState(
        new: AudioPlayerState = AudioPlayerState()
    ) {
        _state.value = new
    }

    private fun getPlayerElement(): HTMLAudioElement? {
        return document.getElementById(htmlId) as? HTMLAudioElement
    }
}