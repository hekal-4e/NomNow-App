package com.android.nomnow.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nomnow_app.ui.theme.NomNowTheme

enum class NomSnackbarType {
    Success, Error, Info
}

@Composable
fun NomSnackbar(
    message: String,
    type: NomSnackbarType = NomSnackbarType.Info,
    modifier: Modifier = Modifier
) {
    val icon = when (type) {
        NomSnackbarType.Success -> Icons.Default.CheckCircle
        NomSnackbarType.Error -> Icons.Default.Error
        NomSnackbarType.Info -> Icons.Default.Info
    }

    val iconColor = when (type) {
        NomSnackbarType.Success -> Color(0xFF34C759)
        NomSnackbarType.Error -> MaterialTheme.colorScheme.error
        NomSnackbarType.Info -> MaterialTheme.colorScheme.primary
    }

    Surface(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant, // surfaceElevated mapping
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NomSnackbarPreview() {
    NomNowTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            NomSnackbar(message = "Order confirmed successfully!", type = NomSnackbarType.Success)
            NomSnackbar(message = "Something went wrong.", type = NomSnackbarType.Error)
            NomSnackbar(message = "Your driver is nearby.", type = NomSnackbarType.Info)
        }
    }
}
