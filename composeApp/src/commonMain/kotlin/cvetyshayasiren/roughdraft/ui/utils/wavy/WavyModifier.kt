package cvetyshayasiren.roughdraft.ui.utils.wavy

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultBlendMode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.wavy(
    crest: WavyCrestStructure = WavyCrestStructure.FromSize(100.dp),
    height: Dp = 40.dp,
    strokeWidth: Dp = 4.dp,
    color: Color = Color.Green,
    start: Offset = Offset(0f, .5f),
    end: Offset = Offset(1f, .5f),
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DefaultBlendMode
) = this then WavyElement(crest, height, strokeWidth, color, start, end, colorFilter, blendMode)

private data class WavyElement(
    val crest: WavyCrestStructure = WavyCrestStructure.FromSize(100.dp),
    val height: Dp = 40.dp,
    val strokeWidth: Dp = 4.dp,
    val color: Color = Color.Green,
    val start: Offset = Offset(0f, .5f),
    val end: Offset = Offset(1f, .5f),
    val colorFilter: ColorFilter? = null,
    val blendMode: BlendMode = DefaultBlendMode
): ModifierNodeElement<WavyNode>(), Modifier {
    override fun create(): WavyNode = WavyNode(crest, height, strokeWidth, color, start, end, colorFilter, blendMode)

    override fun update(node: WavyNode) {
        node.crest = crest
        node.height = height
        node.strokeWidth = strokeWidth
        node.color = color
        node.start = start
        node.end = end
        node.colorFilter = colorFilter
        node.blendMode = blendMode
    }
}

private class WavyNode(
    var crest: WavyCrestStructure = WavyCrestStructure.FromSize(100.dp),
    var height: Dp = 40.dp,
    var strokeWidth: Dp = 4.dp,
    var color: Color = Color.Green,
    var start: Offset = Offset(0f, .5f),
    var end: Offset = Offset(1f, .5f),
    var colorFilter: ColorFilter? = null,
    var blendMode: BlendMode = DefaultBlendMode
): DrawModifierNode, Modifier.Node() {

    override fun ContentDrawScope.draw() {
        val waveWidth = when(crest) {
            is WavyCrestStructure.FromCount -> size.width / (crest as WavyCrestStructure.FromCount).value
            is WavyCrestStructure.FromSize -> (crest as WavyCrestStructure.FromSize).value.toPx()
        }
        val waveHeight = height.toPx()
        val waveAmplitude = waveHeight / 2
        val strokeWidth = strokeWidth.toPx()

        val somePath = Path().apply {
            moveTo(0f, 0f)
            quadraticTo(waveWidth / 4, -waveAmplitude, waveWidth / 2, 0f)
            quadraticTo(waveWidth / 4 * 3, waveAmplitude, waveWidth, 0f)
            lineTo(waveWidth, 0f - strokeWidth)
            quadraticTo(waveWidth / 4 * 3, waveAmplitude - strokeWidth,waveWidth / 2, 0f - strokeWidth)
            quadraticTo(waveWidth / 4, -waveAmplitude - strokeWidth,0f, 0f - strokeWidth)
            translate(Offset(0f, strokeWidth / 2))
        }

        val pathEffect = PathEffect.stampedPathEffect(
            shape = somePath,
            advance = waveWidth,
            phase = 0f,
            style = StampedPathEffectStyle.Rotate
        )

        drawLine(
            color = color,
            start = Offset(size.width * start.x, size.height * start.y),
            end = Offset(size.width * end.x, size.height * end.y),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Butt,
            pathEffect = pathEffect,
            colorFilter = colorFilter,
            blendMode = blendMode
        )
        drawContent()
    }
}

sealed class WavyCrestStructure {
    class FromSize(val value: Dp): WavyCrestStructure()
    class FromCount(val value: Int): WavyCrestStructure()
}