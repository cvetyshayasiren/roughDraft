package cvetyshayasiren.roughdraft.ui.features.settings

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cvetyshayasiren.roughdraft.domain.settings.SettingsState
import cvetyshayasiren.roughdraft.ui.theme.basicText
import cvetyshayasiren.roughdraft.ui.theme.title

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
        Text(
            text = "Настройки",
            style = MaterialTheme.typography.title()
        )

        IconButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                SettingsState
                    .setSettings(themeMode = settings.value.themeMode.switch())
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "тема",
                    style = MaterialTheme.typography.basicText()
                )
                AnimatedContent(
                    targetState = settings.value.themeMode
                ) { state ->
                    Icon(
                        imageVector = state.icon,
                        contentDescription = "${state.label} theme icon"
                    )
                }
            }
        }
    }
}