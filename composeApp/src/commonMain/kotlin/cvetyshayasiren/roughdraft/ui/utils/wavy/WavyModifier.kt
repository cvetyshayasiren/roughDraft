package cvetyshayasiren.roughdraft.ui.utils.wavy

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultBlendMode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

fun Modifier.wavy(
    crest: WavyCrestStructure = WavyCrestStructure.FromLength(100.dp),
    thickness: Dp = 40.dp,
    strokeWidth: Dp = 4.dp,
    color: Color = Color.Green,
    start: Offset = Offset(0f, .5f),
    end: Offset = Offset(1f, .5f),
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DefaultBlendMode
) = this then WavyElement(crest, thickness, strokeWidth, color, start, end, colorFilter, blendMode)

private data class WavyElement(
    val crest: WavyCrestStructure = WavyCrestStructure.FromLength(100.dp),
    val thickness: Dp = 40.dp,
    val strokeWidth: Dp = 4.dp,
    val color: Color = Color.Green,
    val start: Offset = Offset(0f, .5f),
    val end: Offset = Offset(1f, .5f),
    val colorFilter: ColorFilter? = null,
    val blendMode: BlendMode = DefaultBlendMode
): ModifierNodeElement<WavyNode>(), Modifier {
    override fun create(): WavyNode = WavyNode(crest, thickness, strokeWidth, color, start, end, colorFilter, blendMode)

    override fun update(node: WavyNode) {
        node.crest = crest
        node.thickness = thickness
        node.strokeWidth = strokeWidth
        node.color = color
        node.start = start
        node.end = end
        node.colorFilter = colorFilter
        node.blendMode = blendMode
    }
}

private class WavyNode(
    var crest: WavyCrestStructure = WavyCrestStructure.FromLength(100.dp),
    var thickness: Dp = 40.dp,
    var strokeWidth: Dp = 4.dp,
    var color: Color = Color.Green,
    var start: Offset = Offset(0f, .5f),
    var end: Offset = Offset(1f, .5f),
    var colorFilter: ColorFilter? = null,
    var blendMode: BlendMode = DefaultBlendMode
): DrawModifierNode, Modifier.Node() {

    override fun ContentDrawScope.draw() {
        val startX = start.x * size.width
        val startY = start.y * size.height
        val endX = end.x * size.width
        val endY = end.y * size.height

        val waveLength = when(crest) {
            is WavyCrestStructure.FromCount -> {
                val aSide = abs(startX - endX)
                val bSide = abs(startY - endY)
                val diagonal = sqrt(aSide.pow(2) + bSide.pow(2))
                diagonal / (crest as WavyCrestStructure.FromCount).value
            }
            is WavyCrestStructure.FromLength -> (crest as WavyCrestStructure.FromLength).value.toPx()
        }

        if(waveLength == 0f) { return }

        val waveThickness = thickness.toPx()
        val waveAmplitude = waveThickness / 2
        val strokeWidth = strokeWidth.toPx()

        val somePath = Path().apply {
            moveTo(0f, 0f)
            quadraticTo(waveLength / 4, -waveAmplitude, waveLength / 2, 0f)
            quadraticTo(waveLength / 4 * 3, waveAmplitude, waveLength, 0f)
            lineTo(waveLength, 0f - strokeWidth)
            quadraticTo(waveLength / 4 * 3, waveAmplitude - strokeWidth,waveLength / 2, 0f - strokeWidth)
            quadraticTo(waveLength / 4, -waveAmplitude - strokeWidth,0f, 0f - strokeWidth)
            translate(Offset(0f, strokeWidth / 2))
        }

        val pathEffect = PathEffect.stampedPathEffect(
            shape = somePath,
            advance = waveLength,
            phase = 0f,
            style = StampedPathEffectStyle.Rotate
        )

        drawLine(
            color = color,
            start = Offset(startX, startY),
            end = Offset(endX, endY),
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
    class FromLength(val value: Dp): WavyCrestStructure()
    class FromCount(val value: Int): WavyCrestStructure()
}