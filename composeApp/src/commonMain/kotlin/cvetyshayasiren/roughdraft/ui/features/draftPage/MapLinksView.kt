package cvetyshayasiren.roughdraft.ui.features.draftPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import cvetyshayasiren.roughdraft.domain.map.GeoCoordinates
import cvetyshayasiren.roughdraft.domain.map.ThirdPartyMaps
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import cvetyshayasiren.roughdraft.ui.theme.smallText

@Composable
fun MapLinksView(
    modifier: Modifier = Modifier,
    coordinates: GeoCoordinates = GeoCoordinates.SAINT_PETERSBURG
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(space = DesignStyle.bigPadding()),
        verticalArrangement = Arrangement.spacedBy(DesignStyle.smallPadding())
    ) {
        ThirdPartyMaps.list.forEach { map ->
            Text(text = map.annotatedStringLabel)
        }
    }
}