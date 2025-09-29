package cvetyshayasiren.roughdraft.ui.splash

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cvetyshayasiren.roughdraft.domain.splash.SplashState
import cvetyshayasiren.roughdraft.ui.theme.title
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SaluteScreenView(
    text: String = "ЧЕРНОВИК",
    modifier: Modifier = Modifier
) = SaluteScreenView(text = buildAnnotatedString { append(text) }, modifier = modifier)


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SaluteScreenView(
    text: AnnotatedString,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
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