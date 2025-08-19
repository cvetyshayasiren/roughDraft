package cvetyshayasiren.roughdraft.domain.settings

import androidx.compose.ui.graphics.Color
import cvetyshayasiren.roughdraft.domain.audioPlayer.PlaybackOptions
import cvetyshayasiren.roughdraft.domain.map.TileLink
import cvetyshayasiren.roughdraft.ui.theme.ThemeMode

data class SettingsEntity(
    val themeMode: ThemeMode = ThemeMode.DARK,
    val themeSeedColor: Color = Color.Unspecified,
    val tileLink: TileLink = TileLink.WaterColors(),
    val playbackOptions: PlaybackOptions = PlaybackOptions.RepeatNext
)