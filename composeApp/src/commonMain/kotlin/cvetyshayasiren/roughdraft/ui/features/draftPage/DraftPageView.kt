package cvetyshayasiren.roughdraft.ui.features.draftPage

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
            modifier = Modifier
                .verticalScroll(rememberScrollState())
            ,
            targetState = currentPage.value
        ) { page ->
            when(expanded) {
                true -> ExpandedDraftPageView(page = page)
                false -> CompactDraftPageView(page = page)
            }
        }
    }
}