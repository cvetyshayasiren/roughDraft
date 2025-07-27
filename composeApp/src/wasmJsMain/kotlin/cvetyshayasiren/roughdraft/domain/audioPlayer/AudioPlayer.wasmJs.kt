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
        getPlayerElement()?.apply { pause(); remove() }
        document.body?.appendElement("audio") {
            this as HTMLAudioElement
            this.id = htmlId
            this.src = uri
        }
        setState(isReady = true)
    }

    actual fun play(
        coroutineScope: CoroutineScope,
        delay: Long
    ) {
        getPlayerElement()?.play()
        setState(isPlaying = true)

        coroutineScope.launch {
            while(_state.value.isPlaying) {
                getPlayerElement()?.apply {
                    setState(progress = (currentTime / duration).toFloat())
                }
                delay(delay)
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
            setState(isPlaying = false, progress = 0f)
        }
    }

    actual fun setProgress(value: Float) {
        getPlayerElement()?.apply {
            currentTime = value * duration
            setState(progress = value)
        }
    }

    actual fun setVolume(value: Float) {
        getPlayerElement()?.apply {
            volume = value.toDouble()
            setState(volume = value)
        }
    }

    private fun setState(
        isReady: Boolean? = null,
        isPlaying: Boolean? = null,
        progress: Float? = null,
        volume: Float? = null,
        duration: Long? = null
    ) {
        val old = _state.value
        setState(
            AudioPlayerState(
                isReady = isReady ?: old.isReady,
                isPlaying = isPlaying ?: old.isPlaying,
                progress = progress ?: old.progress,
                volume = volume ?: old.volume,
                duration = duration ?: old.duration
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