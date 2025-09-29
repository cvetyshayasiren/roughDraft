package cvetyshayasiren.roughdraft.ui.splash

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import cvetyshayasiren.roughdraft.domain.splash.SplashState
import cvetyshayasiren.roughdraft.domain.splash.SplashStates
import cvetyshayasiren.roughdraft.ui.navigation.RoughDraftAdaptiveNavigation
import kotlinx.coroutines.delay

@Composable
fun SplashScreenView(modifier: Modifier = Modifier) {
    val splashState = SplashState.state.collectAsState()

    LaunchedEffect(Unit) {
        delay(1000)
        SplashState.toPrepared()
    }

    Box(modifier = modifier) {
        RoughDraftAdaptiveNavigation()
        AnimatedVisibility(
            visible = splashState.value.isSalute(),
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)
        ) {
            SaluteScreenView()
        }
    }
}