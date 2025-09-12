package cvetyshayasiren.roughdraft.ui.utils.photo

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberOverscrollEffect
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewModelScope
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftBookInteractions
import cvetyshayasiren.roughdraft.domain.draftsInteractions.PhotoPath
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun PhotoPager(
    modifier: Modifier = Modifier,
    pageSize: Dp,
    photoPaths: List<PhotoPath>,
    scope: CoroutineScope = rememberCoroutineScope()
) {
    val pagerState = rememberPagerState(pageCount = { photoPaths.size })
    val messes = remember { List(photoPaths.size) { PhotoMess.random() } }
    val pagerPadding = DesignStyle.multiBigPadding(4)

    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        pageSize = PageSize.Fixed(pageSize),
        contentPadding = PaddingValues(
            start = pagerPadding,
            end = pageSize
        ),
        snapPosition = SnapPosition.Start,
        pageSpacing = pagerPadding
    ) { pageNumber ->
        val mess = messes[pageNumber]

        PhotoViewer(
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - pageNumber) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue
                    val fraction = 1f - pageOffset.coerceIn(0f, 1f)

                    rotationZ = lerp(
                        start = mess.rotation,
                        stop = 0f,
                        fraction = fraction
                    )
                    scaleX = mess.scale
                    scaleY = mess.scale

                    renderEffect = BlurEffect(
                        radiusX = lerp(
                            start = 8f,
                            stop = 0f,
                            fraction = fraction
                        ),
                        radiusY = lerp(
                            start = 8f,
                            stop = 0f,
                            fraction = fraction
                        ),
                        edgeTreatment = TileMode.Decal
                    )
                }
                .clip(mess.shape)
                .shadow(
                    elevation = DesignStyle.shadowElevation,
                    shape = mess.shape
                ),
            photoPath = photoPaths[pageNumber]
        )
    }

    Row(
        Modifier
            .wrapContentHeight()
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
                scope.launch { pagerState.animateScrollToPreviousPage() }
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowLeft,
                contentDescription = "previous image"
            )
        }
        repeat(pagerState.pageCount) { iteration ->
            val color = animateColorAsState(
                if(pagerState.currentPage == iteration)
                    MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary
            )
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color.value)
                    .size(DesignStyle.multiBigPadding(4))
            )
        }
        IconButton(
            onClick = {
                scope.launch { pagerState.animateScrollToNextPage() }
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                contentDescription = "next image"
            )
        }
    }
}

suspend fun PagerState.animateScrollToNextPage() =
    animateScrollToPage((currentPage + 1).coerceAtMost(pageCount))

suspend fun PagerState.animateScrollToPreviousPage() =
    animateScrollToPage((currentPage - 1).coerceAtLeast(0))