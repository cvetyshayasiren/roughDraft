package cvetyshayasiren.roughdraft.ui.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cvetyshayasiren.roughdraft.domain.settings.SettingsState
import org.jetbrains.compose.resources.InternalResourceApi

@OptIn(InternalResourceApi::class)
@Composable
fun ColorSeedTest() {
    val settings = SettingsState.settings.collectAsState()
    val colors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.error
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center
        ) {
            colors.forEach {
                Box(modifier = Modifier.size(48.dp).background(it))
            }
            Button(onClick = {}) { Text("test btn") }
        }

        RgbPicker(
            label = "pick a coloreo",
            tint = settings.value.themeSeedColor,
        ) {
            SettingsState.setSettings(themeSeedColor = it)
        }
    }

}