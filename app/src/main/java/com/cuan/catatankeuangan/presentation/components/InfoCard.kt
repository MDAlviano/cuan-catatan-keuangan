package com.cuan.catatankeuangan.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color3

@Composable
fun InfoCard(modifier: Modifier, image: Painter, title: String, titleSize: TextUnit = 14.sp, value: String) {
    Box(
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Row {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(5.dp)
                ) {
                    Icon(
                        modifier = if (title == "Pengeluaran" || title == "Laba") {
                            Modifier.rotate(0F)
                        } else {
                            Modifier.rotate(180F)
                        },
                        painter = image,
                        contentDescription = "img",
                        tint = if (title == "Pengeluaran") {
                            Color3
                        } else {
                            Color1
                        }
                    )
                }
                Column(
                    modifier = Modifier.padding(
                        vertical = 3.dp,
                        horizontal = 6.dp
                    )
                ) {
                    Text(
                        text = title,
                        fontSize = titleSize,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 12.dp),
                        thickness = 1.dp,
                        color = Color.White
                    )
                }
            }
            Text(
                modifier = Modifier
                    .padding(top = 2.dp, bottom = 2.dp, end = 12.dp)
                    .fillMaxWidth(),
                text = value,
                color = Color.White,
                fontSize = 22.sp,
                textAlign = TextAlign.End
            )
        }
    }
}
