package cvetyshayasiren.roughdraft.ui.utils.blend

import androidx.annotation.FloatRange
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement

sealed class BackgroundMode {
    data class FromColor(val color: Color): BackgroundMode()
    data class FromBrush(val brush: Brush): BackgroundMode()
}

sealed class SizeMode {
    data class FromFraction(
        val horizontalRange: ClosedFloatingPointRange<Float> = 0f..1f,
        val verticalRange: ClosedFloatingPointRange<Float> = 0f..1f
    ): SizeMode()
    data class FromSize(val topLeft: Offset = Offset.Zero, val size: Size? = null): SizeMode()
}

fun Modifier.blend(
    backgroundMode: BackgroundMode = BackgroundMode.FromColor(color = Color.Unspecified),
    sizeMode: SizeMode = SizeMode.FromFraction(
        horizontalRange = 0f..1f,
        verticalRange = 0f..1f
    ),
    @FloatRange alpha: Float = 1.0f,
    style: DrawStyle = Fill,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = BlendMode.Hue
) = this then BlendElement(
    backgroundMode = backgroundMode,
    sizeMode = sizeMode,
    alpha = alpha,
    style = style,
    colorFilter = colorFilter,
    blendMode = blendMode
)

private data class BlendElement(
    val backgroundMode: BackgroundMode,
    val sizeMode: SizeMode,
    var alpha: Float,
    val style: DrawStyle,
    val colorFilter: ColorFilter?,
    val blendMode: BlendMode
): ModifierNodeElement<BlendNode>(), Modifier {
    override fun create(): BlendNode = BlendNode(
        backgroundMode = backgroundMode,
        sizeMode = sizeMode,
        alpha = alpha,
        style = style,
        colorFilter = colorFilter,
        blendMode = blendMode
    )

    override fun update(node: BlendNode) {
        node.backgroundMode = backgroundMode
        node.sizeMode = sizeMode
        node.alpha = alpha
        node.style = style
        node.colorFilter = colorFilter
        node.blendMode = blendMode
    }
}

private class BlendNode(
    var backgroundMode: BackgroundMode,
    var sizeMode: SizeMode,
    var alpha: Float,
    var style: DrawStyle,
    var colorFilter: ColorFilter?,
    var blendMode: BlendMode
): DrawModifierNode, Modifier.Node() {

    override fun ContentDrawScope.draw() {
        drawContent()

        var topLeft: Offset
        var rectSize: Size

        when(sizeMode) {
            is SizeMode.FromFraction -> {
                val castedSizeMode = sizeMode as SizeMode.FromFraction
                topLeft = Offset(
                    x = size.width * castedSizeMode.horizontalRange.start,
                    y = size.height * castedSizeMode.verticalRange.start
                )
                rectSize = Size(
                    width = size.width * (castedSizeMode.horizontalRange.endInclusive - castedSizeMode.horizontalRange.start),
                    height = size.height * (castedSizeMode.verticalRange.endInclusive - castedSizeMode.verticalRange.start)
                )
            }
            is SizeMode.FromSize -> {
                topLeft = (sizeMode as SizeMode.FromSize).topLeft
                rectSize = (sizeMode as SizeMode.FromSize).size ?: Size(size.width, size.height)
            }
        }

        when(backgroundMode) {
            is BackgroundMode.FromBrush -> {
                drawRect(
                    brush = (backgroundMode as BackgroundMode.FromBrush).brush,
                    topLeft = topLeft,
                    size = rectSize,
                    alpha = alpha,
                    style = style,
                    colorFilter = colorFilter,
                    blendMode = blendMode
                )
            }
            is BackgroundMode.FromColor -> {
                drawRect(
                    color = (backgroundMode as BackgroundMode.FromColor).color,
                    topLeft = topLeft,
                    size = rectSize,
                    alpha = alpha,
                    style = style,
                    colorFilter = colorFilter,
                    blendMode = blendMode
                )
            }
        }
    }
}