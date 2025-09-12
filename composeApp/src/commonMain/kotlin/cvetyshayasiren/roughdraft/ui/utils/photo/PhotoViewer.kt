package cvetyshayasiren.roughdraft.ui.utils.photo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.window.DialogProperties
import com.github.panpf.sketch.AsyncImage
import cvetyshayasiren.roughdraft.domain.draftsInteractions.PhotoPath
import cvetyshayasiren.roughdraft.domain.draftsInteractions.getComposeResourceUri
import cvetyshayasiren.roughdraft.domain.draftsInteractions.getStringPhotoMetaData
import cvetyshayasiren.roughdraft.ui.theme.smallText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoViewer(
    modifier: Modifier = Modifier,
    photoPath: PhotoPath,
    contentScale: ContentScale = ContentScale.Fit
) {
    val enabled = remember { mutableStateOf(false) }

    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }
    val metaData: MutableState<String?> = remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        metaData.value = photoPath.getStringPhotoMetaData()
    }

    AsyncImage(
        modifier = modifier
            .clickable {
                enabled.value = !enabled.value
            },
        uri = photoPath.getComposeResourceUri(),
        contentDescription = "photo",
        contentScale = contentScale,
    )
    AnimatedVisibility(
        visible = enabled.value
    ) {
        BasicAlertDialog(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = rotation,
                    translationX = offset.x,
                    translationY = offset.y
                )
                .transformable(state = state)
                .clickable {
                    enabled.value = false
                },
            onDismissRequest = { enabled.value = false },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Box {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    uri = photoPath.getComposeResourceUri(),
                    contentDescription = "photo"
                )
                AnimatedVisibility(
                    visible = metaData.value != null
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.BottomStart),
                        text = metaData.value ?: "",
                        style = MaterialTheme.typography.smallText()
                    )
                }
            }
        }
    }
}