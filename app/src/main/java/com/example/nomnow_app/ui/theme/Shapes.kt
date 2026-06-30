package com.example.nomnow_app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val NomNowShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp), // radiusXSmall
    small = RoundedCornerShape(8.dp),      // radiusSmall
    medium = RoundedCornerShape(12.dp),    // radiusMedium
    large = RoundedCornerShape(16.dp),     // radiusLarge
    extraLarge = RoundedCornerShape(24.dp) // radiusXLarge
)

// Extension for radiusFull
val RadiusFull = RoundedCornerShape(999.dp)