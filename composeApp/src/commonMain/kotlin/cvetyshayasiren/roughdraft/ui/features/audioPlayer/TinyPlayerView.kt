package cvetyshayasiren.roughdraft.ui.features.audioPlayer

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil3.CoilImage
import cvetyshayasiren.roughdraft.domain.audioPlayer.AudioPlayer
import cvetyshayasiren.roughdraft.domain.audioPlayer.progress
import cvetyshayasiren.roughdraft.domain.audioPlayer.swaPauseIcon
import cvetyshayasiren.roughdraft.domain.audioPlayer.swapPause
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftBookInteractions
import cvetyshayasiren.roughdraft.domain.draftsInteractions.getUri
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import cvetyshayasiren.roughdraft.ui.theme.smallText

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TinyPlayerView(
    modifier: Modifier = Modifier,
    player: AudioPlayer
) {
    val playerState = player.state.collectAsState()
    val currentPage = DraftBookInteractions.currentPage.collectAsState()

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        CoilImage(
            modifier = Modifier
                .size(24.dp)
                .clip(DesignStyle.customShape),
            imageModel = { currentPage.value.iconPath.getUri() }
        )

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = currentPage.value.name,
                style = MaterialTheme.typography.smallText(
                    fontWeight = FontWeight.Thin
                )
            )
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
        AnimatedContent(
            targetState = playerState.value.isPlaying
        ) { isPlaying ->
            Icon(
                modifier = Modifier
                    .clip(DesignStyle.roundedShape)
                    .clickable {
                        player.swapPause()
                    },
                imageVector = playerState.value.swaPauseIcon(),
                contentDescription = "pause/stop button"
            )
        }
    }
}