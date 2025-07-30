package cvetyshayasiren.roughdraft.ui.features.mapDraftBook

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cvetyshayasiren.roughdraft.ui.adaptive.AdaptiveContainer
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
            text = "test",
            style = MaterialTheme.typography.title(color = MaterialTheme.colorScheme.primary)
        )
        AdaptiveContainer(
            content1 = {
                Text(
                    text = "Зачем нам это нужно? Всё просто: RUVDS развивается, каждый год открывая новые площадки с вычислительными мощностями в России и за её пределами. Мы реализуем крутые инициативы и открываем новые перспективы для всей индустрии. А значит, надо ещё плотнее общаться с коллегами, делиться экспертизой, а в каких-то вопросах и вовсе двигаться вместе. Опыт у нас есть, возможности тоже, а теперь — целая команда единомышленников.",
                    style = MaterialTheme.typography.basicText()
                )
            },
            content2 = {
                Text(
                    text = "Прощай,\n" +
                            "позабудь\n" +
                            "и не обессудь.\n" +
                            "А письма сожги,\n" +
                            "как мост.\n" +
                            "Да будет мужественным\n" +
                            "твой путь,\n" +
                            "да будет он прям\n" +
                            "и прост.\n" +
                            "Да будет во мгле\n" +
                            "для тебя гореть\n" +
                            "звёздная мишура,\n" +
                            "да будет надежда\n" +
                            "ладони греть\n" +
                            "у твоего костра.\n" +
                            "Да будут метели,\n" +
                            "снега, дожди\n" +
                            "и бешеный рёв огня,",
                    style = MaterialTheme.typography.basicText()
                )
            },
            leftWeightInRow = .6f
        )
    }
}