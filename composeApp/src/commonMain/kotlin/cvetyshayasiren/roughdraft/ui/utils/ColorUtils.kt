package cvetyshayasiren.roughdraft.ui.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import com.materialkolor.ktx.themeColors

fun calculateSeedColor(bitmap: ImageBitmap): Color {
    val suitableColors = bitmap.themeColors(fallback = Color.Unspecified)
    return suitableColors.first()
}