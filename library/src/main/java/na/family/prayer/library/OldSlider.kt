package na.family.prayer.library
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun OldSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    thumbRadius:Dp = 16.dp,
    trackHeight:Dp = 8.dp,
    colors: SliderColors = SliderDefaults.colors(),
) {
    val density = LocalDensity.current
    var width by remember { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }
    val thumbRadiusPx = with(density) { thumbRadius.toPx() }

    val totalRange = valueRange.endInclusive - valueRange.start
    val stepSize = if (steps > 0) totalRange / (steps + 1) else 0f

    var adjustedValue by remember(value) { mutableFloatStateOf(value) }

    // Adjust value to nearest step when steps > 0
    if (steps > 0) {
        val stepIndex = ((value - valueRange.start) / stepSize).roundToInt()
        adjustedValue = valueRange.start + stepIndex * stepSize
    }

    // Calculate normalized value position (0-1)
    val normalizedValue = (adjustedValue - valueRange.start) / totalRange

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .onGloballyPositioned { coordinates ->
                width = coordinates.size.width.toFloat()
            }
            .pointerInput(Unit) {
                // Detect tap gestures
                detectTapGestures { offset ->
                    val newPosition = (offset.x / width).coerceIn(0f, 1f)
                    var newValue = valueRange.start + totalRange * newPosition

                    if (steps > 0) {
                        val stepIndex = ((newValue - valueRange.start) / stepSize).roundToInt()
                        newValue = valueRange.start + stepIndex * stepSize
                    }

                    onValueChange(newValue.coerceIn(valueRange.start, valueRange.endInclusive))
                }
            }
            .pointerInput(Unit) {
                // Detect drag gestures
                detectDragGestures(
                    onDragStart = { isDragging = true },
                    onDragEnd = { isDragging = false },
                    onDragCancel = { isDragging = false }
                ) { change, _ ->
                    change.consume()
                    val newPosition = (change.position.x / width).coerceIn(0f, 1f)
                    var newValue = valueRange.start + totalRange * newPosition

                    if (steps > 0) {
                        val stepIndex = ((newValue - valueRange.start) / stepSize).roundToInt()
                        newValue = valueRange.start + stepIndex * stepSize
                    }

                    onValueChange(newValue.coerceIn(valueRange.start, valueRange.endInclusive))
                }
            }
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(trackHeight)
                .align(Alignment.Center)
        ) {
            // Draw the track background
            drawRoundRect(
                color = colors.inactiveTrackColor,
                size = Size(width = size.width + (thumbRadius.value * 2), height = size.height),
                cornerRadius = CornerRadius(size.height / 2)
            )

            // Draw the progress track
            drawRoundRect(
                color = colors.activeTrackColor,
                topLeft = Offset((thumbRadius.value * -2), 0f),
                size = Size(width = size.width * normalizedValue, height = size.height),
                cornerRadius = CornerRadius(size.height / 2)
            )
        }

        if (steps > 0) {
            // Draw step indicators
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(trackHeight)
                    .align(Alignment.Center)
            ) {
                val dotRadius = 2.dp.toPx()
                for (i in 1..steps) {
                    val stepPosition = i.toFloat() / (steps + 1)
                    val xPosition = stepPosition * size.width
                    drawCircle(
                        color = Color.White,
                        radius = dotRadius,
                        center = Offset(xPosition, size.height / 2)
                    )
                }
            }
        }

        // Draw the thumb
        Box(
            modifier = Modifier
                .size(thumbRadius * 2)
                .offset {
                    val xOffset = (width * normalizedValue - thumbRadiusPx).roundToInt()
                    IntOffset(xOffset, 0)
                }
                .clip(CircleShape)
                .background(colors.thumbColor)
                .align(Alignment.CenterStart)
        )
    }
}
