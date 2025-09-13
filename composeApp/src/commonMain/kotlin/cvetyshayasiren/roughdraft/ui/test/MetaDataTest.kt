package cvetyshayasiren.roughdraft.ui.test

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.panpf.sketch.AsyncImage
import com.github.panpf.sketch.fetch.newComposeResourceUri
import cvetyshayasiren.roughdraft.domain.draftsInteractions.getCoordinatesMetaData
import cvetyshayasiren.roughdraft.domain.map.getMapState
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
            imagePath.getCoordinatesMetaData { coordinates ->
                mapState.addMarker(
                    id = coordinates.toString(),
                    x = coordinates.x,
                    y = coordinates.y,
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(DesignStyle.customShape),
                        uri = newComposeResourceUri(Res.getUri(imagePath)),
                        contentDescription = null
                    )
                    Text(coordinates.toString(), color = Color.Black)
                }
            }
        }
        MapUI(
            modifier = Modifier.fillMaxWidth().height(600.dp),
            state = mapState
        )
    }
}