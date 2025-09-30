package cvetyshayasiren.roughdraft.ui.features.draftPage

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cvetyshayasiren.roughdraft.Config
import cvetyshayasiren.roughdraft.domain.audioPlayer.AudioPlayerInteractions
import cvetyshayasiren.roughdraft.domain.audioPlayer.swapPauseIcon
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftPageEntity
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import cvetyshayasiren.roughdraft.ui.theme.smallText
import cvetyshayasiren.roughdraft.ui.theme.title

@Composable
fun PlayerCard(
    modifier: Modifier = Modifier,
    page: DraftPageEntity,
) {
    val onColor = page.getOnColor()
    val playerState = AudioPlayerInteractions.player.state.collectAsState()
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(DesignStyle.bigPadding()),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.basicMarquee(),
                text = page.name,
                style = MaterialTheme.typography.title(color = onColor)
            )
            Text(
                modifier = Modifier.basicMarquee(),
                text = page.prettyDate,
                style = MaterialTheme.typography.smallText(color = onColor)
            )
        }

        IconButton(
            onClick = {
                AudioPlayerInteractions.firstPlayInteraction()
            }
        ) {
            AnimatedContent(
                targetState = playerState.value.swapPauseIcon()
            ) { icon ->
                Icon(
                    modifier = Modifier.size(Config.FIRST_PLAY_BUTTON_SIZE),
                    imageVector = icon,
                    contentDescription = "play/pause button"
                )
            }
        }
    }
}