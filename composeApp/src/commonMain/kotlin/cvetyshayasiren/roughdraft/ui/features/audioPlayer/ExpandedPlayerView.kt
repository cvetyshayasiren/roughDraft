package cvetyshayasiren.roughdraft.ui.features.audioPlayer

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import com.github.panpf.sketch.AsyncImage
import cvetyshayasiren.roughdraft.Config
import cvetyshayasiren.roughdraft.domain.audioPlayer.AudioPlayerInteractions
import cvetyshayasiren.roughdraft.domain.audioPlayer.prettyCurrentTime
import cvetyshayasiren.roughdraft.domain.audioPlayer.prettyDuration
import cvetyshayasiren.roughdraft.domain.audioPlayer.swapPauseIcon
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftBookInteractions
import cvetyshayasiren.roughdraft.domain.draftsInteractions.getComposeResourceUri
import cvetyshayasiren.roughdraft.domain.settings.SettingsState
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import cvetyshayasiren.roughdraft.ui.theme.basicText
import cvetyshayasiren.roughdraft.ui.theme.smallText

@Composable
fun ExpandedPlayerView(
    modifier: Modifier = Modifier,
    vm: AudioPlayerInteractions,
    expandedState: MutableState<Boolean>
) {
    val playerState = vm.player.state.collectAsState()
    val currentPage = DraftBookInteractions.currentPage.collectAsState()
    val volumeExpanded = remember { mutableStateOf(false) }
    val settings = SettingsState.settings.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(.38f)
                .clip(DesignStyle.roundedShape)
                .clickable { expandedState.value = false },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = DesignStyle.bigPadding(),
                alignment = Alignment.Start
            )
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(DesignStyle.customShape),
                uri = currentPage.value.iconPath.getComposeResourceUri(),
                contentDescription = "player image"
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = currentPage.value.name,
                    style = MaterialTheme.typography.basicText(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = currentPage.value.prettyDate,
                    style = MaterialTheme.typography.basicText(
                        fontWeight = FontWeight.Thin
                    )
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    expandedState.value = false
                }
            ) {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "collapse player")
            }
        }

        Column(
            modifier = Modifier
                .weight(.24f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedContent(
                targetState = volumeExpanded.value
            ) { state ->
                when(state) {
                    true -> {
                        Slider(
                            value = playerState.value.volume.toFloat(),
                            onValueChange = {
                                vm.setVolume(it.toDouble())
                            }
                        )
                    }
                    false -> PlayerSlider(vm)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = DesignStyle.smallPlusBigPadding()),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = playerState.value.prettyCurrentTime(),
                    style = MaterialTheme.typography.smallText(
                        fontWeight = FontWeight.Thin
                    )
                )
                Text(
                    text = playerState.value.prettyDuration(),
                    style = MaterialTheme.typography.smallText(
                        fontWeight = FontWeight.Thin
                    )
                )
            }
        }

        Row(
            modifier = Modifier
                .weight(.38f)
                .padding(horizontal = DesignStyle.bigPadding()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                modifier = Modifier.size(Config.EXPANDED_PLAYER_SMALL_BUTTON_SIZE),
                onClick = {
                    vm.nextPlaybackOption()
                }
            ) {
                AnimatedContent(
                    targetState = settings.value.playbackOptions,
                    transitionSpec = {
                        fadeIn(animationSpec = spring())
                            .togetherWith(scaleOut(animationSpec = tween(durationMillis = 250))
                        )
                    }
                ) { playback ->
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = playback.icon,
                        contentDescription = ""
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                modifier = Modifier.size(Config.EXPANDED_PLAYER_MEDIUM_BUTTON_SIZE),
                onClick = {
                    DraftBookInteractions.previousPage()
                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Default.SkipPrevious,
                    contentDescription = ""
                )
            }

            IconButton(
                modifier = Modifier.size(Config.EXPANDED_PLAYER_BIG_BUTTON_SIZE),
                onClick = { vm.swapPause() }
            ) {
                AnimatedContent(
                    targetState = playerState.value.swapPauseIcon()
                ) { icon ->
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = icon,
                        contentDescription = "play/stop button"
                    )
                }
            }

            IconButton(
                modifier = Modifier.size(Config.EXPANDED_PLAYER_MEDIUM_BUTTON_SIZE),
                onClick = {
                    DraftBookInteractions.nextPage()
                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Default.SkipNext,
                    contentDescription = ""
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                modifier = Modifier.size(Config.EXPANDED_PLAYER_SMALL_BUTTON_SIZE),
                onClick = {
                    volumeExpanded.value = !volumeExpanded.value
                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                    contentDescription = ""
                )
            }
        }
    }

}