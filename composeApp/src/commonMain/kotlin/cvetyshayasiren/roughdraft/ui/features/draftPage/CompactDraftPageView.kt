package cvetyshayasiren.roughdraft.ui.features.draftPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import cvetyshayasiren.roughdraft.Config
import cvetyshayasiren.roughdraft.domain.audioPlayer.AudioPlayerInteractions
import cvetyshayasiren.roughdraft.domain.audioPlayer.swapPauseIcon
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftPageEntity
import cvetyshayasiren.roughdraft.domain.draftsInteractions.getUri
import cvetyshayasiren.roughdraft.domain.map.CustomMarkers
import cvetyshayasiren.roughdraft.domain.map.getMapState
import cvetyshayasiren.roughdraft.domain.settings.SettingsState
import cvetyshayasiren.roughdraft.ui.features.audioPlayer.AudioPlayerView
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import cvetyshayasiren.roughdraft.ui.theme.basicText
import cvetyshayasiren.roughdraft.ui.theme.smallText
import cvetyshayasiren.roughdraft.ui.theme.title
import cvetyshayasiren.roughdraft.ui.utils.blend.BackgroundMode
import cvetyshayasiren.roughdraft.ui.utils.blend.blend
import cvetyshayasiren.roughdraft.ui.utils.photo.PhotoPager
import cvetyshayasiren.roughdraft.ui.utils.photo.PhotoViewer
import cvetyshayasiren.roughdraft.ui.utils.wavy.WavyHorizontalDivider
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import io.ktor.client.plugins.HttpSend
import org.jetbrains.compose.resources.painterResource
import ovh.plrapps.mapcompose.ui.MapUI
import roughdraft.composeapp.generated.resources.Res
import roughdraft.composeapp.generated.resources.failure

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun CompactDraftPageView(
    page: DraftPageEntity,
    modifier: Modifier = Modifier
) {
    val hazeState = rememberHazeState()
    val onColor = page.getOnColor()
    val settings = SettingsState.settings.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(DesignStyle.bigPadding(), alignment = Alignment.Top),
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .shadow(elevation = DesignStyle.shadowElevation),
            contentAlignment = Alignment.BottomCenter
        ) {
            CoilImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .hazeSource(state = hazeState),
                imageModel = { page.iconPath.getUri() },
                failure = {
                    Image(
                        painter = painterResource(Res.drawable.failure),
                        contentDescription = "on failure result picture"
                    )
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .hazeEffect(
                        state = hazeState,
                        style = HazeMaterials.thin(
                            containerColor = page.color
                        )
                    )
                    .padding(horizontal = DesignStyle.multiBigPadding()),
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
                        if(AudioPlayerInteractions.player.state.value.firstInteractionDone) {
                            AudioPlayerInteractions.swapPause()
                        } else AudioPlayerInteractions.prepareAndPplay()

                    }
                ) {
                    Icon(
                        modifier = Modifier.size(Config.FIRST_PLAY_BUTTON_SIZE),
                        imageVector = AudioPlayerInteractions.player.state.value.swapPauseIcon(),
                        contentDescription = "play/pause button"
                    )
                }
            }
        }

        Text(
            modifier = Modifier
                .padding(start = DesignStyle.multiBigPadding(4)),
            text = page.poem,
            style = MaterialTheme.typography.basicText()
        )

        WavyHorizontalDivider(modifier = Modifier.fillMaxWidth().padding(vertical = DesignStyle.multiBigPadding()))

        Text(
            modifier = Modifier
                .padding(start = DesignStyle.multiBigPadding()),
            text = page.prose,
            style = MaterialTheme.typography.basicText()
        )

        PhotoPager(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(page.color),
            photoPaths = page.photoPaths
        )

        MapUI(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .blend(
                    backgroundMode = BackgroundMode.FromColor(page.color),
                    alpha = .5f
                ),
            state = remember {
                getMapState(
                    initialCoordinates = page.coordinates.toRelativeCoordinates(),
                    initialZoom = 15,
                    customMarkers = CustomMarkers.StaticMiniMapMarker(page),
                    disableGestures = true
                )
            }
        )
    }
}