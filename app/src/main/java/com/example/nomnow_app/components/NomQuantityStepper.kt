package com.android.nomnow.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nomnow_app.ui.theme.NomNowTheme
import com.example.nomnow_app.ui.theme.RadiusFull

@Composable
fun NomQuantityStepper(
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var previousQuantity by remember { mutableStateOf(quantity) }
    var triggerAnimation by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (triggerAnimation) 1.2f else 1f,
        animationSpec = spring(dampingRatio = 0.5f),
        label = "countScale",
        finishedListener = { triggerAnimation = false }
    )

    LaunchedEffect(quantity) {
        if (quantity != previousQuantity) {
            triggerAnimation = true
            previousQuantity = quantity
        }
    }

    Surface(
        modifier = modifier.height(36.dp),
        shape = RadiusFull,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = { if (quantity > 1) onQuantityChange(quantity - 1) },
                enabled = quantity > 1,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    Icons.Default.Remove,
                    contentDescription = "Decrease",
                    modifier = Modifier.size(20.dp),
                    tint = if (quantity > 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                )
            }

            Text(
                text = quantity.toString(),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .widthIn(min = 32.dp)
                    .scale(scale),
                color = MaterialTheme.colorScheme.onSurface
            )

            IconButton(
                onClick = { onQuantityChange(quantity + 1) },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Increase",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NomQuantityStepperPreview() {
    var q by remember { mutableStateOf(1) }
    NomNowTheme {
        Box(Modifier.padding(16.dp)) {
            NomQuantityStepper(quantity = q, onQuantityChange = { q = it })
        }
    }
}
