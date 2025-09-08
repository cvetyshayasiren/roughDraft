package cvetyshayasiren.roughdraft.ui.features.audioPlayer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cvetyshayasiren.roughdraft.domain.audioPlayer.AudioPlayerInteractions
import cvetyshayasiren.roughdraft.domain.audioPlayer.progress

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PlayerSlider(
    vm: AudioPlayerInteractions,
    enabled: Boolean = true
) {
    val playerState = vm.player.state.collectAsState()
    Slider(
        value = playerState.value.progress().toFloat(),
        onValueChange = { value ->
            vm.setProgress(value.toDouble())
        },
        enabled = enabled,
        track = {
            LinearWavyProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = { playerState.value.progress().toFloat() }
            )
        },
        thumb = {
            Icon(
                modifier = Modifier.offset(x = 12.dp, y = (-12).dp),
                imageVector = Icons.AutoMirrored.Filled.DirectionsBike,
                contentDescription = "slider thumb"
            )
        }
    )
}