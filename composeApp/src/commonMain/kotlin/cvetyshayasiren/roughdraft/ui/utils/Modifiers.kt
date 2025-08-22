package cvetyshayasiren.roughdraft.ui.utils

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

fun Modifier.coloredBorder(color: Color = Color.Green, shape: Shape = RoundedCornerShape(0.dp)): Modifier {
    return this then Modifier.border(width = 2.dp, color = color, shape = shape)
}