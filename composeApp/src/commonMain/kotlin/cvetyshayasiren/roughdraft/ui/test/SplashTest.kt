package cvetyshayasiren.roughdraft.ui.test

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import cvetyshayasiren.roughdraft.ui.utils.splash.Splash
import cvetyshayasiren.roughdraft.ui.utils.splash.SplashState
import cvetyshayasiren.roughdraft.ui.utils.splash.delayThenHide
import cvetyshayasiren.roughdraft.ui.utils.splash.rememberSplashState
import kotlinx.coroutines.delay

@Composable
fun SplashTest() {
    val state = rememberSplashState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        state.delayThenHide()
    }

    Splash(
        modifier = Modifier.fillMaxSize(),
        state = state,
    ) {
        Text(text = "CONTENT")
    }
}