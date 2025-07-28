package cvetyshayasiren.roughdraft

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cvetyshayasiren.roughdraft.domain.settings.SettingsState
import cvetyshayasiren.roughdraft.ui.navigation.RoughDraftNavigation
import cvetyshayasiren.roughdraft.ui.theme.RoughDraftExpressiveTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
@Preview
fun App() {
    SettingsState.init(isSystemInDarkTheme())
    RoughDraftExpressiveTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            RoughDraftNavigation(modifier = Modifier.fillMaxSize())
        }
    }
}

//./gradlew kotlinUpgradeYarnLock