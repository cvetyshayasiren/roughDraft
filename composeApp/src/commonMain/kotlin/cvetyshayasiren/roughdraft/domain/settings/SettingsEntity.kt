package cvetyshayasiren.roughdraft.domain.settings

import androidx.compose.ui.graphics.Color
import cvetyshayasiren.roughdraft.ui.theme.ThemeMode

data class SettingsEntity(
    val themeMode: ThemeMode = ThemeMode.DARK,
    val themeSeedColor: Color = Color.Unspecified
)