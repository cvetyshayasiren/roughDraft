package cvetyshayasiren.roughdraft.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class RoughDraftDestination(
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    DraftPane(
        label = "домой",
        icon = Icons.Default.Home,
        contentDescription = "home button"
    ),
//    DraftBook(
//        label = "список",
//        icon = Icons.AutoMirrored.Filled.List,
//        contentDescription = "list button"
//    ),
    MapDraftBook(
        label = "карта",
        icon = Icons.Default.Map,
        contentDescription = "map button"
    ),
    Settings(
        label = "настройки",
        icon = Icons.Default.Settings,
        contentDescription = "settings button"
    )
}



