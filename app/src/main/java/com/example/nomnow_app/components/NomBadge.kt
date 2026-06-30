package com.android.nomnow.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nomnow_app.ui.theme.NomNowTheme
import com.example.nomnow_app.ui.theme.RadiusFull

enum class NomBadgeVariant {
    Success, Error, Warning, Count, Discount
}

@Composable
fun NomBadge(
    text: String,
    variant: NomBadgeVariant,
    modifier: Modifier = Modifier
) {
    when (variant) {
        NomBadgeVariant.Success, NomBadgeVariant.Error, NomBadgeVariant.Warning -> {
            val bgColor = when (variant) {
                NomBadgeVariant.Success -> Color(0xFF34C759) // DarkSuccess
                NomBadgeVariant.Error -> Color(0xFFFF453A)   // DarkError
                else -> Color(0xFFFF9F0A)                    // DarkWarning
            }
            Surface(
                modifier = modifier,
                shape = RadiusFull,
                color = bgColor,
                contentColor = Color.White
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }
        NomBadgeVariant.Count -> {
            Box(
                modifier = modifier
                    .size(20.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelSmall, // Used as caption
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        NomBadgeVariant.Discount -> {
            Surface(
                modifier = modifier,
                shape = MaterialTheme.shapes.extraSmall,
                color = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NomBadgePreview() {
    NomNowTheme {
        Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            NomBadge(text = "Confirmed", variant = NomBadgeVariant.Success)
            NomBadge(text = "3", variant = NomBadgeVariant.Count)
            NomBadge(text = "-20%", variant = NomBadgeVariant.Discount)
        }
    }
}
