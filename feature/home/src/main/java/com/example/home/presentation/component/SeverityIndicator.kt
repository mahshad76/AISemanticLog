package com.example.home.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.home.domain.model.SeverityGroup

@Composable
fun SeverityIndicator(
    percentages: Map<SeverityGroup, Float>,
    modifier: Modifier = Modifier
) {
    val severityColors = mapOf(
        SeverityGroup.DEBUG to Color(0xFF607D8B),
        SeverityGroup.INFO to Color(0xFF2196F3),
        SeverityGroup.WARN to Color(0xFFFFC107),
        SeverityGroup.ERROR to Color(0xFFFF5722),
        SeverityGroup.FATAL to Color(0xFFE91E63),
        SeverityGroup.UNKNOWN to Color(0xFF9E9E9E)
    )

    Canvas(modifier = modifier.size(40.dp)) {
        var startAngle = -90f

        if (percentages.isEmpty()) {
            drawCircle(
                color = Color.LightGray.copy(alpha = 0.3f),
                style = Stroke(width = 8.dp.toPx())
            )
            return@Canvas
        }

        percentages.forEach { (severity, percentage) ->
            val sweepAngle = percentage * 360f
            val color = severityColors[severity] ?: Color.Gray

            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 8.dp.toPx())
            )

            startAngle += sweepAngle
        }
    }
}