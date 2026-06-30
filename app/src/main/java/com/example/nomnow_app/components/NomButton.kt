package com.android.nomnow.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.android.nomnow.core.utils.bounceClick
import com.example.nomnow_app.ui.theme.NomNowTheme
import com.example.nomnow_app.ui.theme.RadiusFull

enum class NomButtonVariant {
    Primary, Secondary, Ghost, Destructive
}

enum class NomButtonSize(val height: Dp) {
    Default(56.dp), Compact(44.dp), Small(36.dp)
}

@Composable
fun NomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: NomButtonVariant = NomButtonVariant.Primary,
    size: NomButtonSize = NomButtonSize.Default,
    isLoading: Boolean = false,
    isOffline: Boolean = false,
    enabled: Boolean = true
) {
    val effectivelyEnabled = enabled && !isLoading && !isOffline
    
    val containerColor = when (variant) {
        NomButtonVariant.Primary -> MaterialTheme.colorScheme.primary
        NomButtonVariant.Secondary -> Color.Transparent
        NomButtonVariant.Ghost -> Color.Transparent
        NomButtonVariant.Destructive -> MaterialTheme.colorScheme.error
    }

    val contentColor = when (variant) {
        NomButtonVariant.Primary -> MaterialTheme.colorScheme.onPrimary
        NomButtonVariant.Secondary -> MaterialTheme.colorScheme.primary
        NomButtonVariant.Ghost -> MaterialTheme.colorScheme.onBackground
        NomButtonVariant.Destructive -> Color.White
    }

    val border = if (variant == NomButtonVariant.Secondary) {
        BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary)
    } else null

    Surface(
        modifier = modifier
            .height(size.height)
            .bounceClick(effectivelyEnabled, onClick),
        shape = RadiusFull,
        color = if (effectivelyEnabled) containerColor else containerColor.copy(alpha = 0.38f),
        contentColor = if (effectivelyEnabled) contentColor else contentColor.copy(alpha = 0.38f),
        border = border
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = contentColor,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Primary Button")
@Composable
fun NomButtonPrimaryPreview() {
    NomNowTheme {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            NomButton(text = "Primary Button", onClick = {})
            NomButton(text = "Loading", onClick = {}, isLoading = true)
            NomButton(text = "Disabled", onClick = {}, enabled = false)
        }
    }
}

@Preview(showBackground = true, name = "Secondary Button")
@Composable
fun NomButtonSecondaryPreview() {
    NomNowTheme {
        NomButton(text = "Secondary Button", variant = NomButtonVariant.Secondary, onClick = {})
    }
}

@Preview(showBackground = true, name = "Ghost & Destructive")
@Composable
fun NomButtonOthersPreview() {
    NomNowTheme {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            NomButton(text = "Ghost Button", variant = NomButtonVariant.Ghost, onClick = {})
            NomButton(text = "Destructive Button", variant = NomButtonVariant.Destructive, onClick = {})
        }
    }
}
