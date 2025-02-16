package com.cuan.catatankeuangan.presentation.screens.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProductScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(24.dp, 12.dp),
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3)
        ) {
            items(5) {
                ProductCard()
            }
        }
    }
}

//@Preview
//@Composable
//fun ProductScreenPreview() {
//    ProductScreen()
//}

@Composable
fun ProductCard() {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.White)
            .height(100.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
//          TODO: Change with Image() composable
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(8.dp, 4.dp))
            Text(text = "test")
        }
    }
}