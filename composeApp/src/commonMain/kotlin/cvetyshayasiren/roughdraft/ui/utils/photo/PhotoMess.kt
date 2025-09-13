package cvetyshayasiren.roughdraft.ui.utils.photo

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import kotlin.random.Random

data class PhotoMess(
    val rotation: Float = 0f,
    val scale: Float = 1f,
    val shape: Shape = RoundedCornerShape(0.dp)
) {
    companion object {
        const val MAX_ROTATION_ANGLE = 25
        const val MAX_SCALE_DEVIATION = 0.1
        const val MIN_ROUNDED = 12
        const val MAX_ROUNDED = 36

        fun random(): PhotoMess = PhotoMess(
            rotation = (-MAX_ROTATION_ANGLE..MAX_ROTATION_ANGLE).random().toFloat(),
            scale = Random.nextDouble(from = 1.0 - MAX_SCALE_DEVIATION, until = 1.0 + MAX_SCALE_DEVIATION).toFloat(),
            shape = RoundedCornerShape(size = (MIN_ROUNDED..MAX_ROUNDED).random().dp)
        )
    }
}
