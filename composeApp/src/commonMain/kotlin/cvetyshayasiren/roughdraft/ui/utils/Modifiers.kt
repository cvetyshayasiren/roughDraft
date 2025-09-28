package cvetyshayasiren.roughdraft.ui.utils

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onLayoutRectChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

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

@Composable
fun Modifier.containerHeightDp(dpStateToWrite: MutableState<Dp>): Modifier {
    val density = LocalDensity.current
    return this then Modifier
        .onLayoutRectChanged { rect ->
            with(density) { dpStateToWrite.value = rect.height.toDp() }
        }
}

@Composable
fun Modifier.containerSizeDp(dpStateToWrite: MutableState<DpSize>): Modifier {
    val density = LocalDensity.current
    return this then Modifier
        .onLayoutRectChanged { rect ->
            with(density) { dpStateToWrite.value = DpSize(width = rect.width.toDp(), height = rect.height.toDp()) }
        }
}

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.onMouseScroll(
    toDo: (isForward: Boolean, change: PointerInputChange) -> Unit
): Modifier {
    return this then Modifier
        .onPointerEvent(PointerEventType.Scroll) { event ->
            val changes = event.changes
            changes.forEach { change ->
                val isForward = change.scrollDelta.y > 0
                toDo(isForward, change)
            }
        }
}

fun Modifier.onHorizontalDrag(
    toDo: (isForward: Boolean, dragAmount: Float) -> Unit
): Modifier {
    return this then Modifier
        .pointerInput(Unit) {
            detectHorizontalDragGestures { _, dragAmount ->
                val isForward = dragAmount <= 0
                toDo(isForward, dragAmount)
            }
        }
}