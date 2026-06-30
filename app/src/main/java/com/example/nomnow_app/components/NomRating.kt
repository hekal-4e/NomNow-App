package com.android.nomnow.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nomnow_app.ui.theme.NomNowTheme

@Composable
fun NomRating(
    rating: Float,
    modifier: Modifier = Modifier,
    reviewCount: Int? = null,
    isInteractive: Boolean = false,
    onRatingChange: (Float) -> Unit = {}
) {
    val starSize = if (isInteractive) 32.dp else 20.dp
    val starColor = Color(0xFFFF9F0A) // Warning/Gold color

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            for (i in 1..5) {
                val isFilled = i <= rating
                Icon(
                    imageVector = if (isFilled) Icons.Default.Star else Icons.Default.StarBorder,
                    contentDescription = null,
                    tint = if (isFilled) starColor else MaterialTheme.colorScheme.outline,
                    modifier = Modifier
                        .size(starSize)
                        .then(
                            if (isInteractive) {
                                Modifier.clickable { onRatingChange(i.toFloat()) }
                            } else Modifier
                        )
                )
            }
        }
        
        if (reviewCount != null) {
            Text(
                text = "($reviewCount)",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NomRatingPreview() {
    NomNowTheme {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Read Only:")
            NomRating(rating = 4.5f, reviewCount = 128)
            
            Text("Interactive:")
            NomRating(rating = 3f, isInteractive = true, onRatingChange = {})
        }
    }
}
