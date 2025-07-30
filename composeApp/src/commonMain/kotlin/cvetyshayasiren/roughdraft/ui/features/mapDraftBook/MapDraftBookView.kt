package cvetyshayasiren.roughdraft.ui.features.mapDraftBook

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cvetyshayasiren.roughdraft.ui.theme.basicText
import cvetyshayasiren.roughdraft.ui.theme.title

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MapDraftBookView(
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "test title",
            style = MaterialTheme.typography.title()
        )
        Text(
            text = "basic test",
            style = MaterialTheme.typography.basicText()
        )
    }
}