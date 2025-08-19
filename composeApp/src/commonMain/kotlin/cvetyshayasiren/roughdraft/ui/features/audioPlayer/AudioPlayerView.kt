package cvetyshayasiren.roughdraft.ui.features.audioPlayer

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cvetyshayasiren.roughdraft.domain.audioPlayer.AudioPlayerInteractions
import cvetyshayasiren.roughdraft.domain.audioPlayer.isFirstInteractionDone
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftBookInteractions
import cvetyshayasiren.roughdraft.domain.settings.SettingsState
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AudioPlayerView(
    modifier: Modifier = Modifier,
    vm: AudioPlayerInteractions = AudioPlayerInteractions
) {
    val player = vm.player
    val playerState = player.state.collectAsState()
    val currentPage = DraftBookInteractions.currentPage.collectAsState()
    val settings = SettingsState.settings.collectAsState()
    val expandedView = remember { mutableStateOf(false) }
    val volumeExpanded = remember { mutableStateOf(false) }

    AnimatedVisibility(
        modifier = modifier,
        visible = playerState.value.isFirstInteractionDone()
    ) {
        AnimatedContent(
            targetState = expandedView.value
        ) { expanded ->
            when(expanded) {
                true -> ExpandedPlayerView()
                false -> TinyPlayerView()
            }
        }
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
            targetState = playerState.value.isPlaying,
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
                        value = playerState.value.volume,
                        onValueChange = { value ->
                            player.setVolume(value)
                        }
                    )
                }
                false -> {
                    Slider(
                        value = playerState.value.progress,
                        onValueChange = { value ->
                            player.setProgress(value)
                        },
                        track = {
                            LinearWavyProgressIndicator(
                                modifier = Modifier.fillMaxWidth(),
                                progress = { playerState.value.progress }
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
            imageVector = settings.value.playbackOptions.icon,
            contentDescription = "swap playback option")
    }
}