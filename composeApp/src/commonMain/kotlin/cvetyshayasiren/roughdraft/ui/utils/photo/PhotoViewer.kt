package cvetyshayasiren.roughdraft.ui.utils.photo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.window.DialogProperties
import com.skydoves.landscapist.coil3.CoilImage
import roughdraft.composeapp.generated.resources.Res

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoViewer(
    modifier: Modifier = Modifier,
    imageUri: String
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

    CoilImage(
        modifier = modifier
            .clickable {
                enabled.value = !enabled.value
            },
        imageModel = { Res.getUri(imageUri) }
    )
    AnimatedVisibility(
        visible = enabled.value
    ) {
        BasicAlertDialog(
            modifier = Modifier.fillMaxSize(),
            onDismissRequest = { enabled.value = false },
            properties = DialogProperties()
        ) {
            CoilImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        enabled.value = false
                    }
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        rotationZ = rotation,
                        translationX = offset.x,
                        translationY = offset.y
                    )
                    .transformable(state = state),
                imageModel = { Res.getUri(imageUri) }
            )
        }
    }
}