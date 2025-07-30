package cvetyshayasiren.roughdraft.ui.window

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowSizeClass

object WindowState {
    @get:Composable
    val currentSize: WindowSizeClass
        get() = currentWindowAdaptiveInfo().windowSizeClass

    @get:Composable
    val isExpanded: Boolean
        get() = currentSize.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)
}

