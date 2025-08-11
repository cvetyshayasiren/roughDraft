package cvetyshayasiren.roughdraft.ui.test

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cvetyshayasiren.roughdraft.ui.navigation.coloredBorder
import cvetyshayasiren.roughdraft.ui.utils.wavy.WavyCrestStructure
import cvetyshayasiren.roughdraft.ui.utils.wavy.WavyHorizontalDivider
import cvetyshayasiren.roughdraft.ui.utils.wavy.WavyVerticalDivider
import cvetyshayasiren.roughdraft.ui.utils.wavy.wavy
import kotlinx.coroutines.delay

data class WavyOpt(
    val color: Color = Color.Green,
    val width: Dp = 200.dp,
    val amplitude: Dp = 24.dp,
    val strokeWidth: Dp = 4.dp
)

val waves = listOf(
    WavyOpt(),
    WavyOpt(color = Color.Blue, width = 100.dp, amplitude = 12.dp, strokeWidth = 8.dp),
    WavyOpt(color = Color.Red, width = 128.dp, amplitude = 24.dp, strokeWidth = 4.dp),
    WavyOpt(color = Color.Cyan, width = 50.dp, amplitude = 12.dp, strokeWidth = 12.dp)
)

@Composable
fun WavyTest() {
    var colorState by remember { mutableStateOf(Color.Green) }
    val color by animateColorAsState(
        targetValue = colorState,
        animationSpec = tween(durationMillis = 500)
    )
    var amplitudeState by remember { mutableStateOf(24.dp) }
    val amplitude by animateDpAsState(
        targetValue = amplitudeState,
        animationSpec = tween(delayMillis = 500)
    )

    LaunchedEffect(Unit) {
        while (true) {
            colorState = randomColor()
            amplitudeState = (8..64).random().dp
            delay(1000)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "WAVY TEST", modifier = Modifier.padding(24.dp))
        waves.forEach { wave ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "width = ${wave.width}\nstrokeWidth = ${wave.strokeWidth}\namplitude = ${wave.amplitude}",
                    modifier = Modifier.weight(.3f),
                    style = MaterialTheme.typography.labelMedium
                )
                Box(
                    modifier = Modifier
                        .weight(.7f)
                        .height(wave.amplitude * 2)
                        .wavy(
                            crest = WavyCrestStructure.FromLength(wave.width),
                            color = wave.color,
                            thickness = wave.amplitude * 2,
                            strokeWidth = wave.strokeWidth
                        )
                )
            }
            HorizontalDivider()
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .wavy(
                    color = color,
                    thickness = amplitude,
                    strokeWidth = 8.dp,
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .wavy(
                    color = color,
                    thickness = amplitude * 2,
                    strokeWidth = 8.dp,
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp)
                .wavy(
                    color = MaterialTheme.colorScheme.primary,
                    start = Offset(.2f, .1f),
                    end = Offset(.8f, .8f)
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp)
                .wavy(
                    crest = WavyCrestStructure.FromCount(5),
                    color = MaterialTheme.colorScheme.secondary,
                )
        )
        WavyHorizontalDivider(modifier = Modifier.fillMaxWidth())
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text("LAL")
            WavyVerticalDivider(modifier = Modifier.width(12.dp).height(128.dp).coloredBorder(Color.Green))
            Text("LOL")
        }
    }
}