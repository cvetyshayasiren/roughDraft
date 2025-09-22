package cvetyshayasiren.roughdraft

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cvetyshayasiren.roughdraft.domain.settings.SettingsState
import cvetyshayasiren.roughdraft.ui.splash.SplashScreenView
import cvetyshayasiren.roughdraft.ui.test.WavyDividerTest
import cvetyshayasiren.roughdraft.ui.theme.DesignStyle
import cvetyshayasiren.roughdraft.ui.theme.RoughDraftExpressiveTheme
import cvetyshayasiren.roughdraft.ui.theme.basicText
import cvetyshayasiren.roughdraft.ui.utils.wavy.WavyVerticalDivider
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
@Preview
fun App() {
    SettingsState.init(isSystemInDarkTheme())
    RoughDraftExpressiveTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            TestR()
//            SplashScreenView()
        }
    }
}

//./gradlew kotlinUpgradeYarnLock

@Composable
fun TestR() {
    val paddingOne = DesignStyle.multiBigPadding()
    val paddingTwo = DesignStyle.multiBigPadding(2)
    val paddingThree = DesignStyle.multiBigPadding(4)
    val text = remember {
        "Инжиниринговый химико-технологический центр Томского государственного университета (ИХТЦ ТГУ) начал производство дигидрата тартрата натрия. В ИХТЦ ТГУ отметили, что материал критически важен для развития отечественной микроэлектроники, а импорт этого вещества в РФ был прекращен после введения санкционных ограничений. \n" +
                "\n" +
                "Работа по проекту «Тартрат» ведётся с 2024 года по заказу и при финансовой поддержке Минпромторга России в рамках программы «Развитие электронного машиностроения на период до 2030 года». Запуск производственной установки произошёл в ходе третьего этапа реализации проекта. Общий объём инвестиций в проект составил 150 млн рублей."
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(space = paddingThree, alignment = Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = paddingTwo, end = paddingOne),
                text = text,
                style = MaterialTheme.typography.basicText()
            )
            WavyVerticalDivider()
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = paddingOne, end = paddingTwo),
                text = text,
                style = MaterialTheme.typography.basicText()
            )
        }
    }
}