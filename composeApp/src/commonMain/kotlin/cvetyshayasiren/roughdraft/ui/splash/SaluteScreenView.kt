package cvetyshayasiren.roughdraft.ui.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cvetyshayasiren.roughdraft.domain.splash.SplashState
import cvetyshayasiren.roughdraft.ui.theme.title
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SaluteScreenView(modifier: Modifier = Modifier) {

    LaunchedEffect(Unit) {
        delay(1000)
        SplashState.nextState()
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ЧЕРНОВИК",
            style = MaterialTheme.typography.title(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            LinearWavyProgressIndicator(
                modifier = Modifier.fillMaxWidth(.4f),
                waveSpeed = 48.dp
            )
            Icon(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.AutoMirrored.Filled.DirectionsBike,
                contentDescription = "bicycle icon"
            )
        }
    }
}