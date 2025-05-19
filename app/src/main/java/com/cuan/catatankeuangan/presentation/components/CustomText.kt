package com.cuan.catatankeuangan.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.cuan.catatankeuangan.presentation.theme.Typography
import com.cuan.catatankeuangan.presentation.theme.interFamily
import com.cuan.catatankeuangan.presentation.utils.formatAbbreviatedNominal

@Composable
fun AutoResizedText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit,
    style: TextStyle
) {
    var defFontSize by remember { mutableStateOf(fontSize) }

    Text(
        text = text,
        modifier = Modifier
            .then(modifier),
        fontSize = defFontSize,
        style = Typography.bodyLarge.merge(style),
        maxLines = 1,
        softWrap = false,
        overflow = TextOverflow.Clip,
        onTextLayout = { result ->
            if (result.didOverflowWidth) {
                defFontSize *= 0.9f
            }
        }
    )
}

@Composable
fun AbbreviatedNominalText(
    value: Long,
    modifier: Modifier = Modifier,
    style: TextStyle,
    fontFamily: FontFamily = interFamily,
    color: Color = Color.Unspecified,
) {
    Text(
        text = "Rp${formatAbbreviatedNominal(value)}",
        style = Typography.bodyLarge.merge(style),
        fontFamily = fontFamily,
        color = color,
        maxLines = 1,
        softWrap = false,
        overflow = TextOverflow.Clip,
        modifier = modifier
    )
}