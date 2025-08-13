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
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftBookInteractions
import cvetyshayasiren.roughdraft.domain.settings.SettingsState
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun DraftBookView(
    modifier: Modifier = Modifier,
    scaffoldNavigator: ThreePaneScaffoldNavigator<Any>
) {
    val pages = DraftBookInteractions.draftBook.collectAsState()
    val scope = DraftBookInteractions.viewModelScope

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
                            scope.launch { scaffoldNavigator.navigateTo(SupportingPaneScaffoldRole.Main) }
                        },
                    page = page
                )
            }
            Spacer(Modifier.height(DesignStyle.bigPadding() * 4))

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