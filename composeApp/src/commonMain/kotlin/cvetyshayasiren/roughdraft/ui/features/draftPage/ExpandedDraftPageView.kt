package cvetyshayasiren.roughdraft.ui.features.draftPage

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftPageEntity

@Composable
fun ExpandedDraftPageView(
    page: DraftPageEntity,
    modifier: Modifier = Modifier

) {
    CompactDraftPageView(page, modifier)
}