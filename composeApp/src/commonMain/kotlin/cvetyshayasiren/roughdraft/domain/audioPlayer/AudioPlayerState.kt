package cvetyshayasiren.roughdraft.domain.audioPlayer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.ui.graphics.vector.ImageVector
import cvetyshayasiren.roughdraft.domain.utils.toPrettyMinSec

data class AudioPlayerState(
    val isReady: Boolean = false,
    val isPlaying: Boolean = false,
    val volume: Double = 0.8,
    val currentTime: Double = 0.0,
    val duration: Double = 0.0,
    val firstInteractionDone: Boolean = false
)

fun AudioPlayerState.progress(): Double =
    if(!duration.isFinite() || duration == 0.0) 0.0 else currentTime / duration
fun AudioPlayerState.isNotReady() = !isReady
fun AudioPlayerState.isOnProgress() = progress() > 0 && progress() < 1
fun AudioPlayerState.isOver() = progress() >= 1f
fun AudioPlayerState.isFirstInteractionDone() = firstInteractionDone
fun AudioPlayerState.swapPauseIcon(): ImageVector =
    if(isPlaying) Icons.Default.PauseCircle else Icons.Default.PlayCircle
fun AudioPlayerState.prettyCurrentTime() = currentTime.toPrettyMinSec()
fun AudioPlayerState.prettyDuration() = duration.toPrettyMinSec()