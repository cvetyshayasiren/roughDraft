package cvetyshayasiren.roughdraft.ui.utils.photo

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import cvetyshayasiren.roughdraft.domain.draftsInteractions.PhotoPath
import kotlin.math.absoluteValue

@Composable
fun PhotoPager(
    modifier: Modifier = Modifier,
    photoPaths: List<PhotoPath>
) {
    val pagerState = rememberPagerState(pageCount = { photoPaths.size })

    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        pageSize = PageSize.Fill,
        contentPadding = PaddingValues(horizontal = 64.dp)
    ) { pageNumber ->
        val mess = remember { PhotoMess.random() }
        PhotoViewer(
            modifier = Modifier
                .fillMaxSize()
                .clip(mess.shape)
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
                    clip = false
                },
            photoPath = photoPaths[pageNumber]
        )
    }
}