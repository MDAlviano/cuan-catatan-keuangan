package com.cuan.catatankeuangan.presentation.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.horizontalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color2

@Composable
fun Button(
    onClick: () -> Unit,
    text: String,
    textColor: Color = Color.White,
    fontSize: TextUnit = MaterialTheme.typography.titleMedium.fontSize,
    gradient: Brush = horizontalGradient(colors = listOf(Color2, Color1)),
    icon: ImageVector? = null
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(),
        onClick = { onClick() },
        shape = RoundedCornerShape(10.dp),
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .width(230.dp)
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = text,
                    fontSize = fontSize,
                    color = textColor,
                    textAlign = TextAlign.Center
                )
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Arrow",
                        tint = textColor
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ButtonPreview() {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { Toast.makeText(context, "Clicked!", Toast.LENGTH_SHORT).show() },
            text = "Login",
            textColor = Color.White,
            icon = Icons.AutoMirrored.Default.ArrowForward
        )
    }
}