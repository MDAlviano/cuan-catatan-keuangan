package com.cuan.catatankeuangan.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cuan.catatankeuangan.presentation.theme.OptionalColor3

@Composable
fun OrLine() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomRowItem(
            weight = 1f,
        )
        Text(
            text = "Atau",
            color = OptionalColor3,
        )
        CustomRowItem(
            weight = 1f,
        )
    }
}

@Composable
fun RowScope.CustomRowItem(weight: Float, color: Color = OptionalColor3) {
    Surface(
        modifier = Modifier
            .width(20.dp)
            .height(1.dp)
            .weight(weight)
            .padding(horizontal = 10.dp),
        color = color
    ) {}
}

@Preview(showBackground = true)
@Composable
private fun OrLinePreview() {
    OrLine()
}