package cvetyshayasiren.roughdraft.ui.features.audioPlayer

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cvetyshayasiren.roughdraft.Config
import cvetyshayasiren.roughdraft.domain.audioPlayer.AudioPlayerInteractions
import cvetyshayasiren.roughdraft.domain.audioPlayer.isFirstInteractionDone
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AudioPlayerView(
    modifier: Modifier = Modifier,
    vm: AudioPlayerInteractions = AudioPlayerInteractions
) {
    val playerState = vm.player.state.collectAsState()
    val expandedState = remember { mutableStateOf(false) }

    AnimatedVisibility(
        modifier = modifier,
        visible = playerState.value.isFirstInteractionDone()
    ) {
        AnimatedContent(
            modifier = Modifier.padding(DesignStyle.multiBigPadding()),
            targetState = expandedState.value
        ) { expanded ->
            when(expanded) {
                true -> ExpandedPlayerView(
                    vm = vm,
                    expandedState = expandedState
                )
                false -> TinyPlayerView(
                    modifier = Modifier
                        .height(Config.TINY_PLAYER_HEIGHT)
                        .clickable {
                            expandedState.value = true
                        },
                    vm = vm
                )
            }
        }
    }
}