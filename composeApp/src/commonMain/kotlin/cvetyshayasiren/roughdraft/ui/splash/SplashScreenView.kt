package cvetyshayasiren.roughdraft.ui.splash

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cvetyshayasiren.roughdraft.domain.splash.SplashState
import cvetyshayasiren.roughdraft.domain.splash.SplashStates
import cvetyshayasiren.roughdraft.ui.navigation.RoughDraftAdaptiveNavigation

@Composable
fun SplashScreenView(modifier: Modifier = Modifier) {
    val splashState = SplashState.state.collectAsState()

    AnimatedContent(
        modifier = modifier,
        targetState = splashState.value
    ) { state ->
        when(state) {
            SplashStates.LOADING -> { LoadingScreenView() }
            SplashStates.SALUTE -> { SaluteScreenView() }
            SplashStates.PREPARED -> { RoughDraftAdaptiveNavigation() }
        }
    }
}