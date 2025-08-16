package cvetyshayasiren.roughdraft.ui.features.audioPlayer

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.automirrored.filled.VolumeUp
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

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Slider(
            modifier = Modifier.fillMaxWidth(),
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

        AnimatedVisibility(
            modifier = Modifier.wrapContentWidth(),
            visible = volumeExpanded.value
        ) {
            Slider(
                value = state.value.volume,
                onValueChange = { value ->
                    player.setVolume(value)
                }
            )
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterHorizontally)
        ) {

            AnimatedContent(
                targetState = state.value.isPlaying,
            ) { isPlaying ->
                IconButton(
                    modifier = Modifier
                        .rotate(90f)
                        .clip(if(isPlaying) MaterialShapes.Bun.toShape() else MaterialShapes.Arrow.toShape())
                        .size(20.dp)
                        .background(accentColor),
                    onClick = {
                        if(isPlaying) player.pause() else player.play()
                    }
                ) { }
            }

            IconButton(
                modifier = Modifier
                    .clip(MaterialShapes.Slanted.toShape())
                    .size(20.dp)
                    .background(accentColor),
                enabled = state.value.isOnProgress(),
                onClick = {
                    player.stop()
                }
            ) { }

            IconButton(
                onClick = {
                    volumeExpanded.value = !volumeExpanded.value
                }
            ) {
                Icon(Icons.AutoMirrored.Filled.VolumeUp, "expand volume slider")
            }
        }
    }
}