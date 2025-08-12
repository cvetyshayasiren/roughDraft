package cvetyshayasiren.roughdraft.ui.theme

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cvetyshayasiren.roughdraft.ui.adaptive.WindowState

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
object DesignStyle {
    @Composable
    fun smallPadding(): Dp = if(WindowState.isExpanded) 4.dp else 2.dp

    @Composable
    fun bigPadding(): Dp = if(WindowState.isExpanded) 8.dp else 4.dp


    @get:Composable
    val customShape get() = MaterialShapes.Pill.toShape()

    @get:Composable
    val markerShape get() = MaterialShapes.Ghostish.toShape()




    val shadowElevation = 2.dp
}