package cvetyshayasiren.roughdraft.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cvetyshayasiren.roughdraft.ui.adaptive.WindowState

object DesignStyle {
    @Composable
    fun defaultPadding(): Dp = if(WindowState.isExpanded) 4.dp else 2.dp

    @Composable
    fun bigPadding(): Dp = if(WindowState.isExpanded) 8.dp else 4.dp

    val defaultShape = RoundedCornerShape(12.dp)
    val defaultElevation = 2.dp
}