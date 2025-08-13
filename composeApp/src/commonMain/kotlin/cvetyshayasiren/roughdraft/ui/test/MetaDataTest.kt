package cvetyshayasiren.roughdraft.ui.test

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ashampoo.kim.Kim
import com.ashampoo.kim.common.convertToPhotoMetadata
import com.ashampoo.kim.format.ImageMetadata
import com.skydoves.landscapist.coil3.CoilImage
import cvetyshayasiren.roughdraft.domain.draftsInteractions.getMetaData
import cvetyshayasiren.roughdraft.domain.map.getMapState
import cvetyshayasiren.roughdraft.domain.map.toRelativeCoordinates
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import ovh.plrapps.mapcompose.api.addMarker
import ovh.plrapps.mapcompose.ui.MapUI
import roughdraft.composeapp.generated.resources.Res

@Composable
fun MetaDataTest() {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imagePath = remember { "files/Чёрная речка/0.jpg" }

        val mapState = remember { getMapState() }

        LaunchedEffect(Unit) {
            imagePath.getMetaData { photoMetaData ->
                photoMetaData.gpsCoordinates?.toRelativeCoordinates()?.let { coordinates ->
                    mapState.addMarker(
                        id = coordinates.toString(),
                        x = coordinates.x,
                        y = coordinates.y,
                    ) {
                        CoilImage(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(DesignStyle.customShape),
                            imageModel = { Res.getUri(imagePath) }
                        )
                        Text(coordinates.toString(), color = Color.Black)
                    }
                }
            }
        }
        MapUI(
            modifier = Modifier.fillMaxWidth().height(600.dp),
            state = mapState
        )
    }
}