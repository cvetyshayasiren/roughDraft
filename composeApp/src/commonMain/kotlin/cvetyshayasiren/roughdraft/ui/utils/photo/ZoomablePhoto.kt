package cvetyshayasiren.roughdraft.ui.utils.photo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.github.panpf.sketch.AsyncImage
import cvetyshayasiren.roughdraft.domain.draftsInteractions.PhotoPath
import cvetyshayasiren.roughdraft.domain.draftsInteractions.getComposeResourceUri
import cvetyshayasiren.roughdraft.domain.draftsInteractions.getStringPhotoMetaData
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import cvetyshayasiren.roughdraft.ui.theme.smallText
import me.saket.telephoto.zoomable.rememberZoomableState
import me.saket.telephoto.zoomable.zoomable

@Composable
fun ZoomablePhoto(
    photoPath: PhotoPath,
    modifier: Modifier = Modifier,
) {
    val metaData: MutableState<String?> = remember { mutableStateOf(null) }
    LaunchedEffect(Unit) {
        metaData.value = photoPath.getStringPhotoMetaData()
    }

    Box(
        modifier = Modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            modifier = modifier
                .fillMaxSize()
                .zoomable(
                    clipToBounds = false,
                    state = rememberZoomableState(),
                ),
            uri = photoPath.getComposeResourceUri(),
            contentDescription = "photo",
            contentScale = ContentScale.Fit
        )

        AnimatedVisibility(
            visible = metaData.value != null,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .fillMaxWidth()
                .wrapContentSize()
                .padding(horizontal = DesignStyle.bigPadding(), vertical = DesignStyle.smallPadding()),
        ) {
            Text(
                text = metaData.value ?: "",
                style = MaterialTheme.typography.smallText()
            )
        }
    }
}