package com.example.shiftracker.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shiftracker.R

// If you add fonts to res/font, reference them here (example using Inter)
val Inter = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_bold, FontWeight.Bold),
    Font(R.font.inter_medium, FontWeight.Medium),
)

private val ModernTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = Inter, fontWeight = FontWeight.Bold, fontSize = 32.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Inter, fontWeight = FontWeight.Medium, fontSize = 22.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Inter, fontWeight = FontWeight.Normal, fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Inter, fontWeight = FontWeight.Medium, fontSize = 14.sp
    )
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF39FF14), // Neon Green
    onPrimary = Color.Black,
    background = Color(0xFF121212),
    surface = Color(0xFF1A1A1A),
    onSurface = Color.White,
    secondary = Color(0xFF1DE9B6),
)

@Composable
fun ShiftTrackerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = ModernTypography,
        shapes = Shapes(
            extraSmall = RoundedCornerShape(12.dp),
            small = RoundedCornerShape(16.dp),
            medium = RoundedCornerShape(24.dp),
            large = RoundedCornerShape(32.dp)
        ),
        content = content
    )
}