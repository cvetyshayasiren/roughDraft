package cvetyshayasiren.roughdraft.ui.adaptive

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.window.core.layout.WindowSizeClass

object WindowState {
    @get:Composable
    val currentSize: WindowSizeClass
        get() = currentWindowAdaptiveInfo().windowSizeClass

    @get:Composable
    val isExpanded: Boolean
        get() = currentSize.isWidthAtLeastBreakpoint(WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND)

    @get:Composable
    val isNotExpanded: Boolean get() = !isExpanded
}