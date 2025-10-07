package cvetyshayasiren.roughdraft.ui.utils.photo

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.toShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.RoundedPolygon
import kotlin.random.Random

data class PhotoMess(
    val shape: Shape = RoundedCornerShape(0.dp)
) {
    companion object {
        const val MIN_ROUNDED = 12
        const val MAX_ROUNDED = 48

        fun random(): PhotoMess = PhotoMess(
            shape = randomShape()
        )

        private fun randomCorner(): Dp = (MIN_ROUNDED..MAX_ROUNDED).random().dp

        @OptIn(ExperimentalMaterial3ExpressiveApi::class)
        private fun randomShape(): Shape {
            val shapes = listOf(
                RoundedCornerShape(
                    topStart = randomCorner(),
                    topEnd = randomCorner(),
                    bottomStart = randomCorner(),
                    bottomEnd = randomCorner()
                )
            )
            return shapes.random()
        }
    }
}
