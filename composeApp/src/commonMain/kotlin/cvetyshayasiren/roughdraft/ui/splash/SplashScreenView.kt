package cvetyshayasiren.roughdraft.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import cvetyshayasiren.roughdraft.ui.navigation.RoughDraftAdaptiveNavigation
import cvetyshayasiren.roughdraft.ui.utils.splash.Splash
import cvetyshayasiren.roughdraft.ui.utils.splash.delayThenHide
import cvetyshayasiren.roughdraft.ui.utils.splash.rememberSplashState

@Composable
fun SplashScreenView(modifier: Modifier = Modifier) {
    val splashState = rememberSplashState()
    LaunchedEffect(Unit) {
        splashState.delayThenHide()
    }

    Splash(
        state = splashState
    ) {
        RoughDraftAdaptiveNavigation()
    }
}