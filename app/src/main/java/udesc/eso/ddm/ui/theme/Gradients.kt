package udesc.eso.ddm.ui.theme

import androidx.compose.ui.graphics.Brush

val BlueGradient = Brush.verticalGradient(
    colors = listOf(GradientBlue10, GradientBlue20),
    startY = 0f,
    endY = 100f
)
val RedGradient = Brush.verticalGradient(
    colors = listOf(GradientRed10, GradientRed20),
    startY = 0f,
    endY = 100f
)
val GreenGradient = Brush.verticalGradient(
    colors = listOf(GradientGreen10, GradientGreen20),
    startY = 0f,
    endY = 100f
)
val OrangeGradient = Brush.verticalGradient(
    colors = listOf(GradientOrange10, GradientOrange20),
    startY = 0f,
    endY = 100f
)

fun getRandomGradient(): Brush {
    val gradientList =
        listOf(BlueGradient, RedGradient , GreenGradient, OrangeGradient)
    return gradientList.random()
}