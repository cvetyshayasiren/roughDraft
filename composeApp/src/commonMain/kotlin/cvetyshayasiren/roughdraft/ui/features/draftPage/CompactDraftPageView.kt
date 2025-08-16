package cvetyshayasiren.roughdraft.ui.features.draftPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftPageEntity
import cvetyshayasiren.roughdraft.domain.draftsInteractions.getUri
import cvetyshayasiren.roughdraft.domain.map.getMapState
import cvetyshayasiren.roughdraft.ui.features.audioPlayer.AudioPlayerView
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import cvetyshayasiren.roughdraft.ui.theme.basicText
import cvetyshayasiren.roughdraft.ui.theme.title
import ovh.plrapps.mapcompose.ui.MapUI

@Composable
fun CompactDraftPageView(
    page: DraftPageEntity
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(DesignStyle.bigPadding(), alignment = Alignment.Top),
        horizontalAlignment = Alignment.Start
    ) {
        CoilImage(
            modifier = Modifier.fillMaxWidth(),
            imageModel = { page.iconPath.getUri() }
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