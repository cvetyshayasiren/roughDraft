package cvetyshayasiren.roughdraft.ui.features.draftPage

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import com.github.panpf.sketch.AsyncImage
import cvetyshayasiren.roughdraft.Config
import cvetyshayasiren.roughdraft.domain.audioPlayer.AudioPlayerInteractions
import cvetyshayasiren.roughdraft.domain.audioPlayer.swapPauseIcon
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftPageEntity
import cvetyshayasiren.roughdraft.domain.draftsInteractions.getComposeResourceUri
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import cvetyshayasiren.roughdraft.ui.theme.smallText
import cvetyshayasiren.roughdraft.ui.theme.title
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun PlayerCard(
    modifier: Modifier = Modifier,
    playCardModifier: Modifier = Modifier,
    page: DraftPageEntity,
) {
    val onColor = page.getOnColor()
    val playerState = AudioPlayerInteractions.player.state.collectAsState()
    val hazeState = rememberHazeState()

    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .hazeSource(state = hazeState),
            uri = page.iconPath.getComposeResourceUri(),
            contentDescription = "draft page image",
            contentScale = ContentScale.FillWidth
        )
        Row(
            modifier = playCardModifier
                .hazeEffect(
                    state = hazeState,
                    style = HazeMaterials.thin(
                        containerColor = page.color
                    )
                ),
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
}