package com.cuan.catatankeuangan.presentation.screens.books

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.presentation.components.CustomTextField
import com.cuan.catatankeuangan.presentation.components.TopBar
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.MainBgColor
import com.cuan.catatankeuangan.presentation.utils.getCustomTopPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DigitalBookBookList(
    digimon: Boolean,
    onDismiss: () -> Unit
) {
    var bookAdder by remember {
        mutableStateOf(false)
    }

    val customTopPadding = getCustomTopPadding(16.dp)
    if (digimon) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MainBgColor)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    TopBar(
                        onClick = onDismiss,
                        text = "Buku Digital"
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color1,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(24),
                        modifier = Modifier
                            .height(48.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 36.dp)
                            .clickable { bookAdder = true }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "Baru",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                            )

                            Spacer(modifier = Modifier.width(2.dp))

                            Icon(
                                painter = painterResource(R.drawable.rounded_add),
                                contentDescription = "New book",
                                modifier = Modifier
                                    .width(16.dp)
                            )
                        }
                    }
                }
            }
            if (bookAdder){
                ModalBottomSheet(
                    onDismissRequest = { bookAdder = false},
                    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                    containerColor = MainBgColor)
                {
                    FormContent {
                        bookAdder= false
                    }
                }}
        }
    }
}

@Composable
fun BookListItem(book: String, isExpanded: Boolean, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color1),
        shape = RoundedCornerShape(24),
        border = BorderStroke(1.dp, Color1),
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .padding(horizontal = 36.dp)
            .clickable { /*TODO*/ }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(painter = painterResource(R.drawable.asians), contentDescription = "")

            Text(
                text = book,
                fontSize = 18.sp,
                color = Color1,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )

            Text(
                text = "8",
                fontSize = 16.sp,
                color = Color1,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}


@Composable
fun FormContent(onSubmit: () -> Unit) {
    var namaToko by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    var kontak by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(24.dp)
            .background(MainBgColor)) {
        Text("Buku Digital Baru",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            label = "Nama Toko",
            fieldValue = namaToko,
            onValueChange = {
                namaToko = it
            },
            hint = "NamaToko",

            )
        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            label = "Alamat",
            fieldValue = alamat,
            onValueChange = {
                alamat = it
            },
            hint = "Alamat",

            )
        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            label = "Kontak",
            fieldValue = kontak,
            onValueChange = {
                kontak = it
            },
            hint = "Kontak",

            )
        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSubmit,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Buat")
        }
    }
}