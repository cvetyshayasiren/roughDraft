package cvetyshayasiren.roughdraft.ui.features.audioPlayer

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cvetyshayasiren.roughdraft.domain.audioPlayer.AudioPlayerInteractions
import cvetyshayasiren.roughdraft.domain.audioPlayer.isFirstInteractionDone

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AudioPlayerView(
    modifier: Modifier = Modifier,
    vm: AudioPlayerInteractions = AudioPlayerInteractions
) {
    val player = vm.player
    val playerState = player.state.collectAsState()
    val expandedView = remember { mutableStateOf(false) }

    AnimatedVisibility(
        modifier = modifier,
        visible = playerState.value.isFirstInteractionDone()
    ) {
        AnimatedContent(
            targetState = expandedView.value
        ) { expanded ->
            when(expanded) {
                true -> ExpandedPlayerView(player = player)
                false -> TinyPlayerView(
                    modifier = Modifier
                        .clickable {
                            expandedView.value = true
                        },
                    player = player
                )
            }
        }
    }
}