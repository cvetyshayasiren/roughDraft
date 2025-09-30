package cvetyshayasiren.roughdraft.ui.utils.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cvetyshayasiren.roughdraft.ui.navigation.RoughDraftAdaptiveNavigation
import cvetyshayasiren.roughdraft.ui.splash.SaluteScreenView

@Composable
fun Splash(
    state: SplashState,
    modifier: Modifier = Modifier,
    splashContent: @Composable () -> Unit = { DefaultSplashScreen() },
    enter: EnterTransition = fadeIn() + expandIn(),
    exit: ExitTransition = shrinkOut() + fadeOut(),
    content: @Composable () -> Unit = { }
) {
    val isSplash = state.isSplash.collectAsState()

    Box(modifier = modifier) {
        content()
        AnimatedVisibility(
            visible = isSplash.value,
            enter = enter,
            exit = exit
        ) {
            splashContent()
        }
    }
}