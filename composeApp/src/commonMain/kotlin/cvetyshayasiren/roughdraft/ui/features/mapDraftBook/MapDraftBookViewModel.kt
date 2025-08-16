package cvetyshayasiren.roughdraft.ui.features.mapDraftBook

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cvetyshayasiren.roughdraft.domain.map.TileCoordinates
import cvetyshayasiren.roughdraft.domain.map.TileLink
import cvetyshayasiren.roughdraft.domain.map.getMapState
import cvetyshayasiren.roughdraft.domain.map.setScale
import cvetyshayasiren.roughdraft.domain.settings.SettingsEntity
import cvetyshayasiren.roughdraft.domain.settings.SettingsState
import cvetyshayasiren.roughdraft.ui.features.settings.SettingsView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ovh.plrapps.mapcompose.api.reloadTiles
import ovh.plrapps.mapcompose.api.scale

class MapDraftBookViewModel: ViewModel() {
    private val settings = SettingsState.settings
    val mapState = getMapState(tileLink = { settings.value.tileLink })

    private val _currentZoom: MutableStateFlow<Int> = MutableStateFlow(TileCoordinates.DEFAULT_ZOOM)
    val currentZoom: StateFlow<Int> = _currentZoom.asStateFlow()

    fun increaseZoom() {
        val tileLink = settings.value.tileLink
        _currentZoom.value = (currentZoom.value + 1)
            .coerceIn(tileLink.minZoom, tileLink.maxZoom)
        mapState.setScale(currentZoom.value)
    }

    fun decreaseZoom() {
        val tileLink = settings.value.tileLink
        _currentZoom.value = (currentZoom.value - 1)
            .coerceIn(tileLink.minZoom, tileLink.maxZoom)
        mapState.setScale(currentZoom.value)
    }

    fun setTileLink(tileLink: TileLink) {
        SettingsState.setSettings(tileLink = tileLink)
        mapState.reloadTiles()
    }
}