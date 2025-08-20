package cvetyshayasiren.roughdraft.ui.features.audioPlayer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cvetyshayasiren.roughdraft.domain.audioPlayer.AudioPlayer
import cvetyshayasiren.roughdraft.domain.audioPlayer.progress

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TinyPlayerView(
    modifier: Modifier = Modifier,
    player: AudioPlayer
) {
    val playerState = player.state.collectAsState()

    Column(
        modifier = modifier
    ) {
        Slider(
            value = playerState.value.progress().toFloat(),
            onValueChange = { value ->
                player.setProgress(value.toDouble())
            },
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
}