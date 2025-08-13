package cvetyshayasiren.roughdraft.ui.features.mapDraftBook

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cvetyshayasiren.roughdraft.domain.map.TileCoordinates
import cvetyshayasiren.roughdraft.domain.map.TileLink
import cvetyshayasiren.roughdraft.domain.map.getMapState
import cvetyshayasiren.roughdraft.domain.map.setScale
import cvetyshayasiren.roughdraft.domain.settings.SettingsState
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import cvetyshayasiren.roughdraft.ui.theme.smallText
import ovh.plrapps.mapcompose.api.reloadTiles
import ovh.plrapps.mapcompose.ui.MapUI

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalComposeUiApi::class)
@Composable
fun MapDraftBookView(
    modifier: Modifier = Modifier,
) {
    val settings = SettingsState.settings.collectAsState()
    val mapState = remember { getMapState(tileLink = { settings.value.tileLink }) }
    val currentZoom = remember { mutableStateOf(TileCoordinates.DEFAULT_ZOOM) }
    val enableSettings = remember { mutableStateOf(false) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        MapUI(
            modifier = Modifier
                .fillMaxSize(),
            state = mapState
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(DesignStyle.bigPadding()),
            verticalArrangement = Arrangement
                .spacedBy(DesignStyle.smallPadding(), alignment = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextButton(
                onClick = {
                    currentZoom.value = (currentZoom.value + 1)
                        .coerceIn(TileCoordinates.MIN_ZOOM, TileCoordinates.MAX_ZOOM)
                    mapState.setScale(currentZoom.value)
                }
            ) {
                Text("+")
            }

            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(shape = DesignStyle.customShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${currentZoom.value}",
                    style = MaterialTheme.typography.smallText(color = MaterialTheme.colorScheme.onPrimary)
                )
            }

            TextButton(
                onClick = {
                    currentZoom.value = (currentZoom.value - 1)
                        .coerceIn(TileCoordinates.MIN_ZOOM, TileCoordinates.MAX_ZOOM)
                    mapState.setScale(currentZoom.value)
                }
            ) {
                Text("-")
            }
        }

        Row(
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            IconButton(
                onClick = {
                    enableSettings.value = !enableSettings.value
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "tile settings button"
                )
            }
            AnimatedVisibility(
                visible = enableSettings.value
            ) {
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState())
                ) {
                    TileLink.TILE_LINKS_LIST.forEach { tileLink ->
                        val mapStateVariant = remember {
                            getMapState(
                                initialZoom = 12,
                                tileLink = { tileLink },
                                disableGestures = true
                            )
                        }
                        MapUI(
                            modifier = modifier
                                .size(200.dp)
                                .clickable {
                                    SettingsState.setSettings(tileLink = tileLink)
                                    mapState.reloadTiles()
                                },
                            state = mapStateVariant
                        )
                    }
                }
            }
        }
    }
}