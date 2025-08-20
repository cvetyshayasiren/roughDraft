package cvetyshayasiren.roughdraft.ui.features.audioPlayer

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cvetyshayasiren.roughdraft.domain.audioPlayer.AudioPlayer

@Composable
fun ExpandedPlayerView(
    modifier: Modifier = Modifier,
    player: AudioPlayer
) {
    Column() {
        TinyPlayerView(player = player)
        Text("EXPANDED")
    }

}