package cvetyshayasiren.roughdraft.ui.utils.photo

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowLeft
import androidx.compose.material.icons.automirrored.outlined.ArrowRight
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import cvetyshayasiren.roughdraft.domain.draftsInteractions.PhotoPaths
import cvetyshayasiren.roughdraft.domain.draftsInteractions.nextIndexLooped
import cvetyshayasiren.roughdraft.domain.draftsInteractions.previousIndexLooped
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoViewerDialog(
    modifier: Modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.surface.copy(alpha = .8f)),
    expandDialog: MutableState<Boolean>,
    photoPaths: PhotoPaths,
    initialPhotoIndex: Int,
    onDismissRequest: () -> Unit = { },
    onNext: () -> Unit = { },
    onPrevious: () -> Unit = { }
) {
    val currentPhotoIndex = remember { mutableStateOf(initialPhotoIndex) }

    AnimatedVisibility(
        visible = expandDialog.value
    ) {
        BasicAlertDialog(
            modifier = modifier,
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = false
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(targetState = currentPhotoIndex.value) { photoIndex ->
                    ZoomablePhoto(photoPath = photoPaths[photoIndex])
                }

                IconButton(
                    modifier = Modifier.align(Alignment.CenterStart).padding(DesignStyle.smallPadding()),
                    onClick = {
                        currentPhotoIndex.value = photoPaths.previousIndexLooped(currentPhotoIndex.value)
                        onPrevious()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowLeft,
                        contentDescription = "previous"
                    )
                }
                IconButton(
                    modifier = Modifier.align(Alignment.CenterEnd).padding(DesignStyle.smallPadding()),
                    onClick = {
                        currentPhotoIndex.value = photoPaths.nextIndexLooped(currentPhotoIndex.value)
                        onNext()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowRight,
                        contentDescription = "next"
                    )
                }
                IconButton(
                    modifier = Modifier.align(Alignment.TopEnd).padding(DesignStyle.smallPadding()),
                    onClick = onDismissRequest
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Cancel,
                        contentDescription = "close"
                    )
                }
            }
        }
    }
}