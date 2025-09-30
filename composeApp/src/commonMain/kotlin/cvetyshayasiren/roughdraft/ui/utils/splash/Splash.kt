package cvetyshayasiren.roughdraft.ui.utils.splash

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier

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
            exit = exit,
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)
        ) {
            splashContent()
        }
    }
}