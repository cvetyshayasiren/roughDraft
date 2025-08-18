package cvetyshayasiren.roughdraft.ui.features.audioPlayer

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cvetyshayasiren.roughdraft.domain.audioPlayer.AudioPlayer
import cvetyshayasiren.roughdraft.domain.audioPlayer.isOnProgress
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AudioPlayerView(
    modifier: Modifier = Modifier,
    accentColor: Color = Color.White,
    audioUri: String
) {
    val player = remember { AudioPlayer() }
    val state = player.state.collectAsState()
    val volumeExpanded = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        player.prepare(audioUri)
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            space = DesignStyle.smallPadding(),
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedContent(
            modifier = Modifier.weight(.05f),
            targetState = state.value.isPlaying,
        ) { isPlaying ->
            Icon(
                modifier = Modifier
                    .clip(DesignStyle.playerIconsShape)
                    .clickable {
                        if(isPlaying) player.pause() else player.play()
                    },
                imageVector = if(isPlaying) Icons.Default.StopCircle else Icons.Default.PlayCircle,
                contentDescription = "play/pause button"
            )
        }

        AnimatedContent(
            modifier = Modifier.weight(.85f),
            targetState = volumeExpanded.value
        ) { expanded ->
            when(expanded) {
                true -> {
                    Slider(
                        value = state.value.volume,
                        onValueChange = { value ->
                            player.setVolume(value)
                        }
                    )
                }
                false -> {
                    Slider(
                        value = state.value.progress,
                        onValueChange = { value ->
                            player.setProgress(value)
                        },
                        track = {
                            LinearWavyProgressIndicator(
                                modifier = Modifier.fillMaxWidth(),
                                progress = { state.value.progress }
                            )
                        },
                        thumb = {
                            Icon(
                                modifier = Modifier.offset(x = 12.dp, y = (-12).dp),
                                imageVector = Icons.AutoMirrored.Filled.DirectionsBike,
                                contentDescription = "slider thumb")
                        }
                    )
                }
            }
        }

        Icon(
            modifier = Modifier
                .weight(.05f)
                .clip(DesignStyle.playerIconsShape)
                .clickable {
                    volumeExpanded.value = !volumeExpanded.value
                },
            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
            contentDescription = "expand volume slider")

        Icon(
            modifier = Modifier
                .weight(.05f)
                .clip(DesignStyle.playerIconsShape)
                .clickable {

                },
            imageVector = state.value.playbackOptions.icon,
            contentDescription = "swap playback option")
    }
}