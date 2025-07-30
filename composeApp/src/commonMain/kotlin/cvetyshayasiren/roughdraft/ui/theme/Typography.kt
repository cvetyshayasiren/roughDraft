package cvetyshayasiren.roughdraft.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.unit.sp
import cvetyshayasiren.roughdraft.ui.adaptive.WindowState
import org.jetbrains.compose.resources.Font
import roughdraft.composeapp.generated.resources.Res
import roughdraft.composeapp.generated.resources.comfortaa_variable
import roughdraft.composeapp.generated.resources.robotoflex_variable

@get:Composable
val comfortaaFont
    get() = FontFamily(
        Font(
            resource = Res.font.comfortaa_variable,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(950),
                FontVariation.width(10f),
                FontVariation.slant(0f),
            )
        )
    )

@get:Composable
val robotoFlexFont
    get() = FontFamily(
        Font(
            resource = Res.font.robotoflex_variable
        )
    )

@Composable
fun Typography.title(
    color: Color = Color.Unspecified
): TextStyle = TextStyle(
    color = color,
    fontSize = if(WindowState.isExpanded) 64.sp else 36.sp,
    fontFamily = comfortaaFont
)


@Composable
fun Typography.basicText(
    color: Color = Color.Unspecified
): TextStyle = TextStyle(
    color = color,
    fontSize = if(WindowState.isExpanded) 32.sp else 20.sp,
    fontFamily = robotoFlexFont
)





