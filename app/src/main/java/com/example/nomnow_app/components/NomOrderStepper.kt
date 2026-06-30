package com.android.nomnow.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nomnow_app.ui.theme.NomNowTheme

enum class NomOrderStepStatus {
    Completed, Active, Upcoming
}

data class NomOrderStep(
    val title: String,
    val subtitle: String? = null,
    val status: NomOrderStepStatus,
    val time: String? = null
)

@Composable
fun NomOrderStepper(
    steps: List<NomOrderStep>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        steps.forEachIndexed { index, step ->
            NomOrderStepItem(
                step = step,
                isLast = index == steps.size - 1
            )
        }
    }
}

@Composable
private fun NomOrderStepItem(
    step: NomOrderStep,
    isLast: Boolean
) {
    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(32.dp)
        ) {
            StepIndicator(status = step.status)
            if (!isLast) {
                VerticalConnector(status = step.status)
            }
        }
        
        Column(
            modifier = Modifier
                .padding(start = 12.dp, bottom = if (isLast) 0.dp else 24.dp)
        ) {
            Text(
                text = step.title,
                style = MaterialTheme.typography.labelLarge,
                color = if (step.status == NomOrderStepStatus.Upcoming) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
            )
            step.subtitle?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (step.status == NomOrderStepStatus.Completed && step.time != null) {
                Text(
                    text = step.time,
                    style = MaterialTheme.typography.labelSmall, // caption mapping
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun StepIndicator(status: NomOrderStepStatus) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(
        modifier = Modifier
            .size(24.dp)
            .then(if (status == NomOrderStepStatus.Active) Modifier.scale(scale) else Modifier)
            .background(
                color = if (status == NomOrderStepStatus.Upcoming) Color.Transparent else MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
            .then(
                if (status == NomOrderStepStatus.Upcoming) 
                    Modifier.background(Color.Transparent, CircleShape)
                    .padding(2.dp)
                    .background(MaterialTheme.colorScheme.outline, CircleShape)
                    .padding(2.dp)
                    .background(MaterialTheme.colorScheme.surface, CircleShape)
                else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        if (status == NomOrderStepStatus.Completed) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
private fun ColumnScope.VerticalConnector(status: NomOrderStepStatus) {
    val color = if (status == NomOrderStepStatus.Completed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    val isDashed = status != NomOrderStepStatus.Completed

    Canvas(modifier = Modifier.weight(1f).width(2.dp)) {
        drawLine(
            color = color,
            start = center.copy(y = 0f),
            end = center.copy(y = size.height),
            strokeWidth = 2.dp.toPx(),
            pathEffect = if (isDashed) PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f) else null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NomOrderStepperPreview() {
    val steps = listOf(
        NomOrderStep("Order Placed", "We have received your order", NomOrderStepStatus.Completed, "12:30 PM"),
        NomOrderStep("Preparing", "The chef is working on it", NomOrderStepStatus.Active),
        NomOrderStep("Out for Delivery", status = NomOrderStepStatus.Upcoming),
        NomOrderStep("Delivered", status = NomOrderStepStatus.Upcoming)
    )
    NomNowTheme {
        Box(Modifier.padding(24.dp)) {
            NomOrderStepper(steps = steps)
        }
    }
}
