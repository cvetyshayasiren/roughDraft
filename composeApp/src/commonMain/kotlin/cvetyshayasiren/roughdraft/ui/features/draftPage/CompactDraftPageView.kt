package cvetyshayasiren.roughdraft.ui.features.draftPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.PlatformContext
import coil3.request.ImageRequest
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import com.skydoves.landscapist.coil3.LocalCoilImageLoader
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftPageEntity
import cvetyshayasiren.roughdraft.domain.draftsInteractions.getUri
import cvetyshayasiren.roughdraft.domain.map.getMapState
import cvetyshayasiren.roughdraft.ui.features.audioPlayer.AudioPlayerView
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import cvetyshayasiren.roughdraft.ui.theme.basicText
import cvetyshayasiren.roughdraft.ui.theme.title
import cvetyshayasiren.roughdraft.ui.utils.blend.BackgroundMode
import cvetyshayasiren.roughdraft.ui.utils.blend.SizeMode
import cvetyshayasiren.roughdraft.ui.utils.blend.blend
import cvetyshayasiren.roughdraft.ui.utils.coloredBorder
import org.jetbrains.compose.resources.painterResource
import ovh.plrapps.mapcompose.ui.MapUI
import roughdraft.composeapp.generated.resources.Res
import roughdraft.composeapp.generated.resources.compose_multiplatform
import roughdraft.composeapp.generated.resources.failure
import kotlin.coroutines.CoroutineContext

@Composable
fun CompactDraftPageView(
    page: DraftPageEntity
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(DesignStyle.bigPadding(), alignment = Alignment.Top),
        horizontalAlignment = Alignment.Start
    ) {
        CoilImage(
            modifier = Modifier
                .fillMaxWidth()
                .clip(DesignStyle.roundedShape)
                .shadow(
                    elevation = DesignStyle.shadowElevation,
                    shape = DesignStyle.roundedShape
                )
                .blend(backgroundMode = BackgroundMode.FromColor(page.color)),
            imageModel = { page.iconPath.getUri() },
            failure = {
                Image(
                    painter = painterResource(Res.drawable.failure),
                    contentDescription = ""
                )
            },

        )

        AudioPlayerView(audioUri = page.audioUri)

        Text(page.name, style = MaterialTheme.typography.title())
        HorizontalDivider()
        Text(page.prose, style = MaterialTheme.typography.basicText())
        MapUI(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            state = remember {
                getMapState(
                    initialCoordinates = page.coordinates.toRelativeCoordinates(),
                    initialZoom = 15,
                    disableGestures = true
                )
            }
        )
    }
}