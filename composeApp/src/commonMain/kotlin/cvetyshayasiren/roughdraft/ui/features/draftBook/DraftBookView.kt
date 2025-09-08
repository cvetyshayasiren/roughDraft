package cvetyshayasiren.roughdraft.ui.features.draftBook

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftBookInteractions
import cvetyshayasiren.roughdraft.domain.settings.SettingsState
import cvetyshayasiren.roughdraft.ui.navigation.RoughDraftDestination
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun DraftBookView(
    modifier: Modifier = Modifier,
    currentDestination: MutableState<RoughDraftDestination>
) {
    val pages = DraftBookInteractions.draftBook.collectAsState()

    val settings = SettingsState.settings.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            pages.value.forEach { page ->
                DraftBookPageCard(
                    modifier = Modifier
                        .background(page.getBrush())
                        .clickable {
                            DraftBookInteractions.setPage(page.name)
                            currentDestination.value = RoughDraftDestination.DraftPane

                        },
                    page = page
                )
            }
            Spacer(Modifier.height(DesignStyle.multiBigPadding(4)))

            IconButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    SettingsState
                        .setSettings(themeMode = settings.value.themeMode.switch())
                }
            ) {
                AnimatedContent(
                    targetState = settings.value.themeMode
                ) { state ->
                    Icon(
                        imageVector = state.icon,
                        contentDescription = "${state.label} theme icon"
                    )
                }
            }
        }
    }
}