package cvetyshayasiren.roughdraft.ui.features.audioPlayer

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import com.github.panpf.sketch.AsyncImage
import cvetyshayasiren.roughdraft.domain.audioPlayer.AudioPlayerInteractions
import cvetyshayasiren.roughdraft.domain.audioPlayer.swapPauseIcon
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftBookInteractions
import cvetyshayasiren.roughdraft.domain.draftsInteractions.getComposeResourceUri
import cvetyshayasiren.roughdraft.domain.draftsInteractions.getUri
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import cvetyshayasiren.roughdraft.ui.theme.basicText

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TinyPlayerView(
    modifier: Modifier = Modifier,
    vm: AudioPlayerInteractions
) {
    val playerState = vm.player.state.collectAsState()
    val currentPage = DraftBookInteractions.currentPage.collectAsState()

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxHeight()
                .clip(DesignStyle.customShape),
            uri = currentPage.value.iconPath.getComposeResourceUri(),
            contentDescription = "tiny player image"
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.padding(DesignStyle.bigPadding()),
                text = currentPage.value.name,
                style = MaterialTheme.typography.basicText(
                    fontWeight = FontWeight.Thin
                )
            )
            PlayerSlider(vm, enabled = false)
        }
        AnimatedContent(
            targetState = playerState.value.swapPauseIcon()
        ) { icon ->
            Icon(
                modifier = Modifier
                    .clip(DesignStyle.roundedShape)
                    .clickable {
                        vm.swapPause()
                    },
                imageVector = icon,
                contentDescription = "pause/stop button"
            )
        }
    }
}