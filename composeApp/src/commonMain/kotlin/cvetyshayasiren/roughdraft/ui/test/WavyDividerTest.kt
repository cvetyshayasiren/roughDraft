package cvetyshayasiren.roughdraft.ui.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.VerticalSlider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import cvetyshayasiren.roughdraft.ui.utils.coloredBorder
import cvetyshayasiren.roughdraft.ui.utils.wavy.WavyHorizontalDivider
import cvetyshayasiren.roughdraft.ui.utils.wavy.WavyVerticalDivider
import kotlinx.serialization.builtins.ArraySerializer

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun WavyDividerTest() {
    val horizontalDividerSize = remember { mutableStateOf(DpSize(100.dp, 5.dp)) }
    val verticalDividerSize = remember { mutableStateOf(DpSize(5.dp, 100.dp)) }
    val colorOne = MaterialTheme.colorScheme.inversePrimary
    val colorTwo = MaterialTheme.colorScheme.tertiary

    Row(
        modifier = Modifier.fillMaxSize(.8f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.weight(1f).fillMaxSize().background(colorOne),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.weight(7f),
                contentAlignment = Alignment.Center
            ) {
                WavyHorizontalDivider(
                    modifier = Modifier
                        .size(horizontalDividerSize.value)
                        .clip(DesignStyle.roundedShape)
                        .background(colorTwo)
                )
            }
            Column(
                modifier = Modifier.weight(3f).padding(horizontal = DesignStyle.smallPadding()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Text("w " + "${horizontalDividerSize.value.width.value.toInt()}".padStart(3, '0'))
                    Slider(
                        value = horizontalDividerSize.value.width.value,
                        valueRange = 0f..200f,
                        onValueChange = {
                            horizontalDividerSize.value = DpSize(width = it.toInt().dp, height = horizontalDividerSize.value.height)
                        }
                    )
                }
                Row {
                    Text("h " + "${horizontalDividerSize.value.height.value.toInt()}".padStart(2, '0'))
                    Slider(
                        value = horizontalDividerSize.value.height.value,
                        valueRange = 0f..80f,
                        onValueChange = {
                            horizontalDividerSize.value = DpSize(width = horizontalDividerSize.value.width, height = it.toInt().dp)
                        }
                    )
                }
            }
        }

        //
        Column(
            modifier = Modifier.weight(1f).fillMaxSize().background(colorTwo),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.weight(7f),
                contentAlignment = Alignment.Center
            ) {
                WavyVerticalDivider(
                    modifier = Modifier
                        .size(verticalDividerSize.value)
                        .clip(DesignStyle.roundedShape)
                        .background(colorOne)
                )
            }
            Column(
                modifier = Modifier.weight(3f).padding(horizontal = DesignStyle.smallPadding()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Text("w " + "${verticalDividerSize.value.width.value.toInt()}".padStart(2, '0'))
                    Slider(
                        value = verticalDividerSize.value.width.value,
                        valueRange = 0f..80f,
                        onValueChange = {
                            verticalDividerSize.value = DpSize(width = it.toInt().dp, height = verticalDividerSize.value.height)
                        }
                    )
                }
                Row {
                    Text("h " + "${verticalDividerSize.value.height.value.toInt()}".padStart(3, '0'))
                    Slider(
                        value = verticalDividerSize.value.height.value,
                        valueRange = 0f..200f,
                        onValueChange = {
                            verticalDividerSize.value = DpSize(width = verticalDividerSize.value.width, height = it.toInt().dp)
                        }
                    )
                }
            }
        }
    }
}