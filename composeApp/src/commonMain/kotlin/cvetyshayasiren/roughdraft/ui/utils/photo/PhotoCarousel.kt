package cvetyshayasiren.roughdraft.ui.utils.photo

import androidx.annotation.FloatRange
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.CarouselState
import androidx.compose.material3.carousel.HorizontalCenteredHeroCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.github.panpf.sketch.AsyncImage
import cvetyshayasiren.roughdraft.domain.draftsInteractions.PhotoPaths
import cvetyshayasiren.roughdraft.domain.draftsInteractions.getComposeResourceUri
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import cvetyshayasiren.roughdraft.ui.utils.onHorizontalDrag
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoCarousel(
    modifier: Modifier = Modifier,
    @FloatRange(0.0, 1.0)
    photoPaths: PhotoPaths,
    scope: CoroutineScope = rememberCoroutineScope()
) {
    val itemCount = remember { photoPaths.size }
    val messes = remember { List(photoPaths.size) { PhotoMess.random() } }
    val carouselState = rememberCarouselState(itemCount = { itemCount })
    val expandDialog = remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        HorizontalCenteredHeroCarousel(
            modifier = Modifier
                .weight(.9f)
                .onHorizontalDrag { isForward, _ ->
                    val pageToScroll = if(isForward) 1 else -1
                    scope.launch {
                        carouselState.animateScrollToItem(carouselState.currentItem + pageToScroll)
                    }
                },
            state = carouselState,
            itemSpacing = DesignStyle.bigPadding(),
            contentPadding = PaddingValues(DesignStyle.smallPadding())
        ) {pageNumber ->
            val mess = remember { messes[pageNumber] }
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .maskClip(mess.shape)
                    .clickable { expandDialog.value = !expandDialog.value },
                uri = photoPaths[pageNumber].getComposeResourceUri(),
                contentDescription = "photo",
                contentScale = ContentScale.Crop
            )
        }

        Row(
            Modifier
                .weight(.1f)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement
                .spacedBy(
                    space = DesignStyle.smallPadding(),
                    alignment = Alignment.CenterHorizontally
                )
        ) {
            IconButton(
                onClick = {
                    scope.launch { carouselState.animateScrollToPreviousPage(itemCount) }
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "previous image"
                )
            }
            repeat(photoPaths.size) { iteration ->
                val color = animateColorAsState(
                    if(carouselState.currentItem == iteration)
                        MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary
                )
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color.value)
                        .size(DesignStyle.multiBigPadding(2))
                )
            }
            IconButton(
                onClick = {
                    scope.launch { carouselState.animateScrollToNextPage(itemCount) }
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "next image"
                )
            }
        }

        PhotoViewerDialog(
            expandDialog = expandDialog,
            photoPaths = photoPaths,
            initialPhotoIndex = carouselState.currentItem,
            onDismissRequest = { expandDialog.value = false },
            onNext = { scope.launch { carouselState.animateScrollToNextPage(itemCount) } },
            onPrevious = { scope.launch { carouselState.animateScrollToPreviousPage(itemCount) } }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
suspend fun CarouselState.animateScrollToNextPage(itemCount: Int? = null) =
    animateScrollToItem(
        item = when(itemCount) {
            currentItem + 1 -> 0
            else -> currentItem + 1
        }
    )

@OptIn(ExperimentalMaterial3Api::class)
suspend fun CarouselState.animateScrollToPreviousPage(itemCount: Int? = null) =
    animateScrollToItem(
        item = if(currentItem == 0) itemCount ?: 0 else currentItem - 1
    )