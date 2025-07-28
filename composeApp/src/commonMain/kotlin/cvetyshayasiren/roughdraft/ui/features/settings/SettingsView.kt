package cvetyshayasiren.roughdraft.ui.features.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cvetyshayasiren.roughdraft.domain.settings.SettingsState

@Composable
fun SettingsView(
    modifier: Modifier = Modifier
) {
    val settings = SettingsState.settings.collectAsState()
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("SettingsView")
        Checkbox(
            checked = settings.value.darkTheme,
            onCheckedChange = {
                SettingsState.setSettings(darkTheme = it)
            },
        )
    }
}