package cvetyshayasiren.roughdraft.ui.utils.photo

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
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

    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        pageSize = PageSize.Fixed(pageSize),
        contentPadding = PaddingValues(
            start = DesignStyle.multiBigPadding(4),
            end = DesignStyle.multiBigPadding(16)
        )
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

                    translationX = lerp(
                        start = mess.offset.x,
                        stop = 0f,
                        fraction = fraction
                    )
                    translationY = lerp(
                        start = mess.offset.y,
                        stop = 0f,
                        fraction = fraction
                    )
                    rotationZ = lerp(
                        start = mess.rotation,
                        stop = 0f,
                        fraction = fraction
                    )
                    scaleX = lerp(
                        start = mess.scale,
                        stop = 1f,
                        fraction = fraction
                    )
                    scaleY = lerp(
                        start = mess.scale,
                        stop = 1f,
                        fraction = fraction
                    )

                    renderEffect = BlurEffect(
                        radiusX = lerp(
                            start = 4f,
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
                    alpha = lerp(
                        start = .6f,
                        stop = 1f,
                        fraction = fraction
                    )
                }
                .clip(mess.shape),
            photoPath = photoPaths[pageNumber]
        )
    }
    Row(

    ) {
        IconButton(
            onClick = {
                scope.launch { pagerState.animateScrollToPreviousPage() }
            }
        ) {
            Text("<")
        }
        IconButton(
            onClick = {
                scope.launch { pagerState.animateScrollToNextPage() }
            }
        ) {
            Text(">")
        }
    }
}

suspend fun PagerState.animateScrollToNextPage() =
    animateScrollToPage((currentPage + 1).coerceAtMost(pageCount))

suspend fun PagerState.animateScrollToPreviousPage() =
    animateScrollToPage((currentPage - 1).coerceAtLeast(0))