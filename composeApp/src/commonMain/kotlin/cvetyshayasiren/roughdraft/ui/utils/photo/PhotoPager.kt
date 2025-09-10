package cvetyshayasiren.roughdraft.ui.utils.photo

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import cvetyshayasiren.roughdraft.domain.draftsInteractions.PhotoPath
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import kotlin.math.absoluteValue

@Composable
fun PhotoPager(
    modifier: Modifier = Modifier,
    pageSize: Dp,
    photoPaths: List<PhotoPath>
) {
    val pagerState = rememberPagerState(pageCount = { photoPaths.size })

    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        pageSize = PageSize.Fixed(pageSize),
        contentPadding = PaddingValues(horizontal = DesignStyle.multiBigPadding(4))
    ) { pageNumber ->
        val mess = remember { PhotoMess.random() }

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
                            start = 4f,
                            stop = 0f,
                            fraction = fraction
                        )
                    )
                }
                .clip(mess.shape),
            photoPath = photoPaths[pageNumber]
        )
    }
}