package cvetyshayasiren.roughdraft.domain.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil3.CoilImage
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftBookInteractions
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftPageEntity
import cvetyshayasiren.roughdraft.domain.draftsInteractions.getMetaData
import cvetyshayasiren.roughdraft.domain.draftsInteractions.getUri
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import cvetyshayasiren.roughdraft.ui.theme.smallText
import cvetyshayasiren.roughdraft.ui.utils.photo.PhotoViewer
import ovh.plrapps.mapcompose.api.ExperimentalClusteringApi
import ovh.plrapps.mapcompose.api.addClusterer
import ovh.plrapps.mapcompose.api.addMarker
import ovh.plrapps.mapcompose.ui.state.MapState
import ovh.plrapps.mapcompose.ui.state.markers.model.RenderingStrategy

sealed interface CustomMarkers {
    val pages: List<DraftPageEntity> get() = DraftBookInteractions.draftBook.value

    fun behaviour(state: MapState) {
        state.apply {
            pages.forEach { page ->
                addMarker(
                    id = page.name,
                    x = page.coordinates.lon.toRelative(),
                    y = page.coordinates.lat.toRelative(),
                    relativeOffset = Offset(-.5f, -.5f)
                ) {
                    View(page)
                }
            }
        }
    }

    @Composable
    fun View(page: DraftPageEntity) {
        CoilImage(
            modifier = Modifier
                .size(48.dp)
                .clip(shape = DesignStyle.markerShape)
                .shadow(
                    elevation = DesignStyle.shadowElevation,
                    shape = DesignStyle.markerShape
                ),
            imageModel = { page.iconPath.getUri() }
        )
    }

    class StaticMiniMapMarker(override val pages: List<DraftPageEntity>): CustomMarkers

    class EnhancedMapMarker(
        override val pages: List<DraftPageEntity> = DraftBookInteractions.draftBook.value
    ): CustomMarkers {

        @OptIn(ExperimentalClusteringApi::class)
        override fun behaviour(state: MapState) {
            state.apply {
                pages.forEach { page ->
                    state.addClusterer(id = page.name) { ids ->
                        { Cluster(size = ids.size, page = page) }
                    }
                    addMarker(
                        id = page.name,
                        x = page.coordinates.lon.toRelative(),
                        y = page.coordinates.lat.toRelative(),
                        relativeOffset = Offset(-.5f, -.5f),
                        renderingStrategy = RenderingStrategy.Clustering(page.name)
                    ) {
                        View(page)
                    }
                    page.photoPaths.forEachIndexed { index, imagePath ->
                        imagePath.getMetaData { metadata ->
                            metadata.gpsCoordinates?.toRelativeCoordinates()?.let { coordinates ->
                                addMarker(
                                    id = page.name + index,
                                    x = coordinates.x,
                                    y = coordinates.y,
                                    relativeOffset = Offset(-.5f, -.5f),
                                    renderingStrategy = RenderingStrategy.Clustering(page.name)
                                ) {
                                    PhotoViewer(
                                        modifier = Modifier
                                            .size(48.dp),
                                        imagePath = imagePath
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        @Composable
        override fun View(page: DraftPageEntity) {
            CoilImage(
                modifier = Modifier
                    .size(48.dp)
                    .clip(shape = DesignStyle.markerShape)
                    .background(page.color)
                    .shadow(
                        elevation = DesignStyle.shadowElevation,
                        shape = DesignStyle.markerShape
                    ),
                imageModel = { page.iconPath.getUri() }
            )
        }

        @Composable
        private fun Cluster(size: Int, page: DraftPageEntity) {
            Box(
                modifier = Modifier
                    .background(
                        page.color,
                        shape = CircleShape
                    )
                    .size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = size.toString(),
                    style = MaterialTheme.typography.smallText(color = page.getOnColor()),
                )
            }
        }
    }
}