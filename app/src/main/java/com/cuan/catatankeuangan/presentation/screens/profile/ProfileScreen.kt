package com.cuan.catatankeuangan.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Magenta),
        contentAlignment = Alignment.Center
    ) {

        val names = listOf("Name 1", "Name 2", "Name 3")

        NameList(names = names)
    }
}

@Composable
fun NameList(names: List<String>) {

    LazyColumn {
        items(names) { name ->
            ListItem(name = name)
        }
    }

}

@Composable
fun ListItem(name: String) {
    Card {
        Text(text = name)
    }
}