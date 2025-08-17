package cvetyshayasiren.roughdraft.ui.utils

import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.materialkolor.ktx.themeColors

fun Modifier.coloredBorder(color: Color = Color.Green): Modifier {
    return this then Modifier.border(width = 2.dp, color = color)
}