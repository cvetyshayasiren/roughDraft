package cvetyshayasiren.roughdraft.ui.features.draftPage

import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftBookInteractions
import cvetyshayasiren.roughdraft.ui.adaptive.WindowState

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun DraftPageView(
    modifier: Modifier = Modifier,
) {
    val isExpanded = WindowState.isExpanded
    val currentPage = DraftBookInteractions.currentPage.collectAsState()
    AnimatedContent(
        modifier = modifier,
        targetState = isExpanded
    ) { expanded ->
        AnimatedContent(
            targetState = currentPage.value
        ) { page ->
            when(expanded) {
                true -> ExpandedDraftPageView(page)
                false -> CompactDraftPageView(page)
            }
        }
    }
}