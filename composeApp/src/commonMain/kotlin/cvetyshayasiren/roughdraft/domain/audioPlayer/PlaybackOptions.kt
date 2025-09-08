package cvetyshayasiren.roughdraft.domain.audioPlayer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.PlayDisabled
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.ui.graphics.vector.ImageVector

enum class PlaybackOptions(
    val label: String,
    val icon: ImageVector
) {
    Stoppable(
        label = "остановить",
        icon = Icons.Default.PlayDisabled
    ),
    RepeatOne(
        label = "повторять один трек",
        icon = Icons.Default.RepeatOne
    ),
    RepeatNext(
        label = "перейти дальше",
        icon = Icons.Default.AllInclusive
    )
}

fun PlaybackOptions.next(): PlaybackOptions {
    return when(this) {
        PlaybackOptions.Stoppable -> PlaybackOptions.RepeatOne
        PlaybackOptions.RepeatOne -> PlaybackOptions.RepeatNext
        PlaybackOptions.RepeatNext -> PlaybackOptions.Stoppable
    }
}
