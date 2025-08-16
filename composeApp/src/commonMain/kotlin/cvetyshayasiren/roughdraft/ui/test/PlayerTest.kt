package cvetyshayasiren.roughdraft.ui.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cvetyshayasiren.roughdraft.ui.features.audioPlayer.AudioPlayerView
import roughdraft.composeapp.generated.resources.Res

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PlayerTest() {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AudioPlayerView(
            modifier = Modifier
                .width(600.dp)
                .wrapContentHeight()
                .clip(MaterialShapes.Slanted.toShape())
                .background(Color(105, 105, 210))
                .padding(48.dp),
            audioUri = Res.getUri("files/train.mp3")
        )
    }
}