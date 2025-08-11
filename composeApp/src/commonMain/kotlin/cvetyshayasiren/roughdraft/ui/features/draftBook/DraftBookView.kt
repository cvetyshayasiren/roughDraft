package cvetyshayasiren.roughdraft.ui.features.draftBook

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SweepGradient
import androidx.compose.ui.modifier.modifierLocalConsumer
import com.materialkolor.ktx.darken
import com.materialkolor.ktx.lighten
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftBookInteractions
import cvetyshayasiren.roughdraft.domain.settings.SettingsState
import cvetyshayasiren.roughdraft.domain.settings.themeModeIsDark
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import cvetyshayasiren.roughdraft.ui.theme.basicText
import cvetyshayasiren.roughdraft.ui.theme.title
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun DraftBookView(
    modifier: Modifier = Modifier,
    scaffoldNavigator: ThreePaneScaffoldNavigator<Any>
) {
    val pages = DraftBookInteractions.draftBook.collectAsState()
    val scope = rememberCoroutineScope()
    val isDark = SettingsState.themeModeIsDark(scope = scope).collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        if (scaffoldNavigator.scaffoldValue[SupportingPaneScaffoldRole.Main] == PaneAdaptedValue.Hidden) {
            IconButton(
                onClick = {
                    scope.launch {
                        scaffoldNavigator.navigateTo(SupportingPaneScaffoldRole.Main)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Sharp.ArrowBack,
                    contentDescription = "Back icon button"
                )
            }
        }

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            pages.value.forEach { page ->
                val pageColor by animateColorAsState(
                    if(isDark.value) page.color.darken(1.2f) else page.color.lighten(1.2f)
                )
                val pageColor2 by animateColorAsState(
                    if(isDark.value) page.color.darken(1.8f) else page.color.lighten(1.8f)
                )

                DraftBookPageCard(
                    modifier = Modifier
                        .background(brush = Brush.horizontalGradient(listOf(pageColor, pageColor2)))
                        .clickable {
                            DraftBookInteractions.setPage(page.name)
                            scope.launch { scaffoldNavigator.navigateTo(SupportingPaneScaffoldRole.Main) }
                        },
                    page = page,
                )
            }
            Spacer(Modifier.height(DesignStyle.bigPadding()* 4))
        }
    }
}