package com.android.nomnow.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nomnow_app.ui.theme.NomNowTheme

@Composable
fun ShimmerSkeleton(
    modifier: Modifier = Modifier,
    shape: androidx.compose.ui.graphics.Shape = MaterialTheme.shapes.medium
) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "translate"
    )

    val shimmerColors = listOf(
        MaterialTheme.colorScheme.surface,
        MaterialTheme.colorScheme.surfaceVariant, // surfaceElevated mapping
        MaterialTheme.colorScheme.surface
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    Box(
        modifier = modifier
            .background(brush, shape)
    )
}

@Preview(showBackground = true)
@Composable
fun ShimmerSkeletonPreview() {
    NomNowTheme {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // Card Skeleton
            ShimmerSkeleton(Modifier.fillMaxWidth().height(100.dp))
            
            // List Item Skeleton
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ShimmerSkeleton(Modifier.size(80.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ShimmerSkeleton(Modifier.width(200.dp).height(20.dp))
                    ShimmerSkeleton(Modifier.width(150.dp).height(16.dp))
                }
            }
            
            // Banner Skeleton
            ShimmerSkeleton(
                modifier = Modifier.fillMaxWidth().height(180.dp),
                shape = MaterialTheme.shapes.large
            )
        }
    }
}
