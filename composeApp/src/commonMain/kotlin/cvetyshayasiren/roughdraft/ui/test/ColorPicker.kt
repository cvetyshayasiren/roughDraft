package cvetyshayasiren.roughdraft.ui.test

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Casino
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastRoundToInt
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle

val colors = listOf(Color.Black, Color.White, Color.Red, Color.Green, Color.Blue)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RgbPicker(
    label: String,
    tint: Color,
    alpha: Boolean = true,
    onClick: (color: Color) -> Unit
) {
    var alert by remember { mutableStateOf(false) }
    val defaultShape = remember { RoundedCornerShape(12.dp) }
    val defaultElevation = remember { 2.dp }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(DesignStyle.smallPadding())
            .shadow(elevation = defaultElevation, shape = defaultShape)
            .clip(defaultShape)
            .background(MaterialTheme.colorScheme.surface)
            .border(width = defaultElevation, color = tint, shape = defaultShape)
            .size(120.dp, 48.dp)
            .clickable { alert = true }
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(start = DesignStyle.smallPadding() * 2)
                .basicMarquee(),
            text = label,
            fontSize = MaterialTheme.typography.labelSmall.fontSize,
            textAlign = TextAlign.Start
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = DesignStyle.smallPadding() * 4)
                .fillMaxSize()
                .clip(
                    RoundedCornerShape(
                        topStart = 12.dp,
                        topEnd = 0.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 12.dp
                    )
                )
                .background(tint)
        )
    }

    if(alert) {
        BasicAlertDialog(
            onDismissRequest = { alert = false },
            content = {
                Column(
                    modifier = Modifier
                        .clip(defaultShape)
                        .background(tint.copy(alpha = .9f))
                        .padding(DesignStyle.smallPadding() * 2),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Slider(
                        value = tint.red,
                        valueRange = (0f..1f),
                        onValueChange = {
                            onClick(tint.copy(red = it))
                        },
                        colors = SliderDefaults.colors(
                            activeTrackColor = Color.Red.copy(alpha = .5f),
                            inactiveTrackColor = Color.Red.copy(alpha = .1f)
                        ),
                        thumb = { MyThumb((tint.red * 255).toInt(), Color.Red) }
                    )
                    Slider(
                        value = tint.green,
                        valueRange = (0f..1f),
                        onValueChange = {
                            onClick(tint.copy(green = it))
                        },
                        colors = SliderDefaults.colors(
                            activeTrackColor = Color.Green.copy(alpha = .5f),
                            inactiveTrackColor = Color.Green.copy(alpha = .1f)
                        ),
                        thumb = { MyThumb((tint.green * 255).toInt(), Color.Green) }
                    )
                    Slider(
                        value = tint.blue,
                        valueRange = (0f..1f),
                        onValueChange = {
                            onClick(tint.copy(blue = it))
                        },
                        colors = SliderDefaults.colors(
                            activeTrackColor = Color.Blue.copy(alpha = .5f),
                            inactiveTrackColor = Color.Blue.copy(alpha = .1f)
                        ),
                        thumb = { MyThumb((tint.blue * 255).toInt(), Color.Blue) }
                    )
                    if(alpha) {
                        Slider(
                            value = tint.alpha,
                            valueRange = (0f..1f),
                            onValueChange = {
                                onClick(tint.copy(alpha = it))
                            },
                            colors = SliderDefaults.colors(
                                activeTrackColor = Color.Black.copy(alpha = tint.alpha),
                                inactiveTrackColor = Color.Black.copy(alpha = tint.alpha)
                            ),
                            thumb = { MyThumb("${(tint.alpha * 100).fastRoundToInt()}%", Color.Black.copy(alpha = .5f)) }
                        )
                    }

                    FlowRow {
                        colors.forEach { color ->
                            Button(
                                modifier = Modifier
                                    .padding(DesignStyle.smallPadding())
                                    .size(DesignStyle.smallPadding() * 4),
                                shape = defaultShape,
                                colors = ButtonDefaults.buttonColors(containerColor = color),
                                onClick = { onClick(color) },
                            ) { }
                        }
                    }

                    Row(
                        Modifier.fillMaxWidth(.9f),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        IconButton(
                            onClick = {
                                onClick(randomColor())
                            }
                        ) {
                            Icon(Icons.Rounded.Casino, "randomise icon")
                        }
                        TextButton(
                            onClick = {
                                alert = false
                            },
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                contentColor = tint
                            )
                        ) {
                            Text("Close")
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun MyThumb(value: String? = null, color: Color = MaterialTheme.colorScheme.primary) {
    val defaultShape = remember { RoundedCornerShape(12.dp) }
    val defaultElevation = remember { 2.dp }
    Box(
        Modifier
            .size(60.dp, 30.dp)
            .border(width = 2.dp, color = color, shape = defaultShape)
            .clip(defaultShape)
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        if(value != null) {
            Text(
                modifier = Modifier.fillMaxSize(),
                text = value.toString(),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun MyThumb(value: Int? = null, color: Color = MaterialTheme.colorScheme.primary) {
    MyThumb(value = value.toString(), color = color)
}