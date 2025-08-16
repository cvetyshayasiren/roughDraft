package cvetyshayasiren.roughdraft.ui.features.mapDraftBook

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
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
    vm: MapDraftBookViewModel = remember { MapDraftBookViewModel() }
) {
    val currentZoom = vm.currentZoom.collectAsState()
    val enableSettings = remember { mutableStateOf(false) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        MapUI(
            modifier = Modifier
                .fillMaxSize(),
            state = vm.mapState
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
                    vm.increaseZoom()
                }
            ) {
                Text("+")
            }

            TextButton(
                onClick = {
                    vm.decreaseZoom()
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
                                    vm.setTileLink(tileLink)
                                },
                            state = mapStateVariant
                        )
                    }
                }
            }
        }
    }
}