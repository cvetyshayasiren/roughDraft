package cvetyshayasiren.roughdraft.domain.audioPlayer

data class AudioPlayerState(
    val isReady: Boolean = false,
    val isPlaying: Boolean = false,
    val progress: Float = 0f,
    val volume: Float = 1f,
    val duration: Long = 0,
    val playbackOptions: PlaybackOptions = PlaybackOptions.RepeatNext
)

fun AudioPlayerState.isNotReady() = !this.isReady
fun AudioPlayerState.isOnProgress() = this.progress > 0
