package cvetyshayasiren.roughdraft.ui.adaptive

import androidx.annotation.FloatRange
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.window.core.layout.WindowSizeClass

@Composable
fun AdaptiveContainer(
    modifier: Modifier = Modifier,
    content1: @Composable (BoxScope.() -> Unit) = { },
    content2: @Composable (BoxScope.() -> Unit) = { },
    @FloatRange(from = 0.0, to = 1.0)
    leftWeightInRow: Float = .5f
) {
    AnimatedContent(
        modifier = modifier,
        targetState = WindowState.isExpanded
    ) { state ->
        when(state) {
            true -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier.weight(leftWeightInRow),
                        contentAlignment = Alignment.Center
                    ) {
                        content1()
                    }
                    Box(
                        modifier = Modifier.weight(1f - leftWeightInRow),
                        contentAlignment = Alignment.Center
                    ) {
                        content2()
                    }
                }
            }
            false -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        content1()
                    }
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        content2()
                    }
                }
            }
        }
    }
}