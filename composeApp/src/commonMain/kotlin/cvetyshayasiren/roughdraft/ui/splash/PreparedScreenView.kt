package cvetyshayasiren.roughdraft.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cvetyshayasiren.roughdraft.ui.features.audioPlayer.AudioPlayerView
import cvetyshayasiren.roughdraft.ui.navigation.RoughDraftAdaptiveNavigation

@Composable
fun PreparedScreenView(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        RoughDraftAdaptiveNavigation()
        AudioPlayerView(modifier = Modifier.align(Alignment.BottomCenter))
    }
}