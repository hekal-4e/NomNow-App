package com.android.nomnow.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.nomnow.core.utils.bounceClick
import com.example.nomnow_app.ui.theme.NomNowTheme

enum class NomCardVariant {
    Default, Elevated, Interactive
}

@Composable
fun NomCard(
    modifier: Modifier = Modifier,
    variant: NomCardVariant = NomCardVariant.Default,
    isFeatureCard: Boolean = false,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val shape = if (isFeatureCard) MaterialTheme.shapes.large else MaterialTheme.shapes.medium
    
    val shadowElevation = if (!isDark && variant == NomCardVariant.Elevated) 4.dp else 0.dp
    val border = if (isDark) BorderStroke(1.dp, MaterialTheme.colorScheme.outline) else null

    Surface(
        modifier = modifier.then(
            if (variant == NomCardVariant.Interactive && onClick != null) {
                Modifier.bounceClick(onClick = onClick)
            } else Modifier
        ),
        shape = shape,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = if (variant == NomCardVariant.Elevated) 2.dp else 0.dp,
        shadowElevation = shadowElevation,
        border = border,
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun NomCardPreview() {
    NomNowTheme {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            NomCard(Modifier.fillMaxWidth().height(100.dp)) {
                Box(Modifier.padding(16.dp)) { Text("Default Card") }
            }
            NomCard(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                variant = NomCardVariant.Elevated
            ) {
                Box(Modifier.padding(16.dp)) { Text("Elevated Card") }
            }
            NomCard(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                variant = NomCardVariant.Interactive,
                onClick = {}
            ) {
                Box(Modifier.padding(16.dp)) { Text("Interactive Card (Click me)") }
            }
        }
    }
}
