package cvetyshayasiren.roughdraft.ui.theme

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cvetyshayasiren.roughdraft.ui.adaptive.WindowState

object DesignStyle {
    @Composable
    fun defaultPadding(): Dp = if(WindowState.isExpanded) 4.dp else 2.dp

    @Composable
    fun bigPadding(): Dp = if(WindowState.isExpanded) 8.dp else 4.dp

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    @get:Composable
    val customShape get() = MaterialShapes.Pill.toShape()

    val shadowElevation = 2.dp
}