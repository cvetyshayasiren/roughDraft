package cvetyshayasiren.roughdraft.ui.utils.photo

import androidx.annotation.FloatRange
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.CarouselState
import androidx.compose.material3.carousel.HorizontalCenteredHeroCarousel
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import cvetyshayasiren.roughdraft.domain.draftsInteractions.PhotoPath
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import cvetyshayasiren.roughdraft.ui.utils.onHorizontalDrag
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoCarousel(
    modifier: Modifier = Modifier,
    @FloatRange(0.0, 1.0)
    photoPaths: List<PhotoPath>,
    scope: CoroutineScope = rememberCoroutineScope()
) {
    val messes = remember { List(photoPaths.size) { PhotoMess.random() } }
    val carouselState = rememberCarouselState(itemCount = {photoPaths.size})

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
            PhotoViewer(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .maskClip(mess.shape),
                photoPath = photoPaths[pageNumber]
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
                    scope.launch { carouselState.animateScrollToPreviousPage() }
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
                    scope.launch { carouselState.animateScrollToNextPage() }
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "next image"
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
suspend fun CarouselState.animateScrollToNextPage() =
    animateScrollToItem(currentItem + 1)

@OptIn(ExperimentalMaterial3Api::class)
suspend fun CarouselState.animateScrollToPreviousPage() =
    animateScrollToItem((currentItem - 1).coerceAtLeast(0))