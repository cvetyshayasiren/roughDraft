package cvetyshayasiren.roughdraft.domain.audioPlayer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class AudioPlayer() {
    val state: StateFlow<AudioPlayerState>

    fun prepare(uri: String)

    fun play(
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
        delay: Long = 100
    )

    fun pause()

    fun stop()

    fun setProgress(value: Float)

    fun setVolume(value: Float)
}

data class AudioPlayerState(
    val isReady: Boolean = false,
    val isPlaying: Boolean = false,
    val progress: Float = 0f,
    val volume: Float = 1f,
    val duration: Long = 0,
)

fun AudioPlayerState.isNotReady() = !this.isReady
fun AudioPlayerState.isOnProgress() = this.progress > 0