package cvetyshayasiren.roughdraft.ui.utils

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onLayoutRectChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.coloredBorder(color: Color = Color.Green, shape: Shape = RoundedCornerShape(0.dp)): Modifier {
    return this then Modifier.border(width = 2.dp, color = color, shape = shape)
}

@Composable
fun Modifier.containerWidthDp(dpStateToWrite: MutableState<Dp>): Modifier {
    val density = LocalDensity.current
    return this then Modifier
        .onLayoutRectChanged { rect ->
            with(density) { dpStateToWrite.value = rect.width.toDp() }
        }
}