package cvetyshayasiren.roughdraft.ui.features.mapDraftBook

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cvetyshayasiren.roughdraft.ui.adaptive.AdaptiveContainer
import cvetyshayasiren.roughdraft.ui.test.MapTestViewModel
import cvetyshayasiren.roughdraft.ui.theme.basicText
import cvetyshayasiren.roughdraft.ui.theme.title
import ovh.plrapps.mapcompose.ui.MapUI

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MapDraftBookView(
    modifier: Modifier = Modifier,
) {
    val mapState = remember { MapDraftBookViewModel.state }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MapUI(
            modifier = Modifier
                .fillMaxSize(),
            state = mapState
        )
    }
}