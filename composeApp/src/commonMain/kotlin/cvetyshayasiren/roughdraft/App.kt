package cvetyshayasiren.roughdraft

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cvetyshayasiren.roughdraft.ui.navigation.RoughDraftNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
@Preview
fun App() {
    MaterialExpressiveTheme(
        colorScheme = darkColorScheme(),
        motionScheme = MotionScheme.expressive()
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            RoughDraftNavigation(modifier = Modifier.fillMaxSize())
        }
    }
}

//./gradlew kotlinUpgradeYarnLock