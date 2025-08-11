package cvetyshayasiren.roughdraft.ui.utils.wavy

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultBlendMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun WavyHorizontalDivider(
    modifier: Modifier,
    crest: WavyCrestStructure = WavyCrestStructure.FromCount(4),
    height: Dp = 20.dp,
    strokeWidth: Dp = 2.dp,
    color: Color = MaterialTheme.colorScheme.onSurface,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DefaultBlendMode
) {
    Box(
        modifier = modifier
            .wavy(
                crest = crest,
                thickness = height,
                strokeWidth = strokeWidth,
                 color = color,
                colorFilter = colorFilter,
                blendMode = blendMode
            )
    )
}

@Composable
fun WavyVerticalDivider(
    modifier: Modifier,
    crest: WavyCrestStructure = WavyCrestStructure.FromCount(4),
    height: Dp = 20.dp,
    strokeWidth: Dp = 2.dp,
    color: Color = MaterialTheme.colorScheme.onSurface,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DefaultBlendMode
) {
    Box(
        modifier = modifier
            .wavy(
                crest = crest,
                thickness = height,
                strokeWidth = strokeWidth,
                color = color,
                colorFilter = colorFilter,
                blendMode = blendMode,
                start = Offset(.5f, 0f),
                end = Offset(.5f, 1f),
            )
    )
}