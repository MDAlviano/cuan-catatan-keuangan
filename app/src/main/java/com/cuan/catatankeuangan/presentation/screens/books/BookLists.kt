package com.cuan.catatankeuangan.presentation.screens.books

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.data.local.entities.Book
import com.cuan.catatankeuangan.data.local.entities.BookType
import com.cuan.catatankeuangan.presentation.components.CustomTextField
import com.cuan.catatankeuangan.presentation.components.TopBar
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color2
import com.cuan.catatankeuangan.presentation.theme.MainBgColor
import com.cuan.catatankeuangan.presentation.theme.OptionalColor3
import com.cuan.catatankeuangan.presentation.theme.ralewayFamily
import com.cuan.catatankeuangan.viewmodel.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookList(
    bookViewModel: BookViewModel,
    showDialog: Boolean,
    onDismiss: () -> Unit
) {

    val bookList by bookViewModel.allBooks.observeAsState(initial = emptyList())

    var showNewBookSheet by remember { mutableStateOf(false) }
//    var showEditCategory by remember { mutableStateOf(false) }
//    var showDeleteCategory by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MainBgColor)
            ) {
                NewBookSheet(
                    bookViewModel = bookViewModel,
                    showSheet = showNewBookSheet,
                    sheetState = sheetState,
                    onDismiss = {
                        showNewBookSheet = false
                    },
                    onConfirm = {
                        showNewBookSheet = false
                    }
                )

                Column {
                    TopBar(onClick = onDismiss, text = "Buku Digital")

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(36.dp, 0.dp, 36.dp, 12.dp)
                    ) {

                        if (bookList.isEmpty()) {
                            item {
                                Text(
                                    text = "Belum ada buku. Klik tombol Baru untuk menambahkan buku baru. ",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        } else {
                            items(bookList) { book ->
                                BookListItem(book = book, onClick = {})
                            }
                        }

                        item {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = Color1,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(24),
                                modifier = Modifier
                                    .height(48.dp)
                                    .fillMaxWidth()
                                    .clickable { showNewBookSheet = true }
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
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewBookSheet(
    bookViewModel: BookViewModel,
    showSheet: Boolean,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {

    val context = LocalContext.current

    var selectedBookType by remember { mutableStateOf("Pribadi") }
    var nameFieldValue by remember { mutableStateOf("") }
    var descriptionFieldValue by remember { mutableStateOf("") }
    var addressFieldValue by remember { mutableStateOf("") }
    var contactFieldValue by remember { mutableStateOf("") }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = MainBgColor,
            modifier = Modifier.fillMaxHeight()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "Buat Buku Baru",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color2
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    AssistChip(
                        onClick = {
                            selectedBookType = "Pribadi"
                            nameFieldValue = ""
                            descriptionFieldValue = ""
                            addressFieldValue = ""
                            contactFieldValue = ""
                        },
                        label = {
                            Text(
                                text = "Buku Pribadi",
                                fontFamily = ralewayFamily,
                                fontSize = 16.sp,
                                modifier = Modifier.weight(1f)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Buku pribadi",
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .size(32.dp)
                            )
                        },
                        shape = RoundedCornerShape(100),
                        border = BorderStroke(1.dp, Color1),
                        colors = if (selectedBookType == "Pribadi") {
                            AssistChipDefaults.assistChipColors(
                                containerColor = Color1,
                                labelColor = Color.White,
                                leadingIconContentColor = Color.White
                            )
                        } else {
                            AssistChipDefaults.assistChipColors(
                                containerColor = Color.Unspecified,
                                labelColor = Color1,
                                leadingIconContentColor = Color1
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    )

                    AssistChip(
                        onClick = {
                            selectedBookType = "Bisnis"
                            nameFieldValue = ""
                            descriptionFieldValue = ""
                            addressFieldValue = ""
                            contactFieldValue = ""
                        },
                        label = {
                            Text(
                                text = "Buku Bisnis",
                                fontFamily = ralewayFamily,
                                fontSize = 16.sp,
                                modifier = Modifier.weight(1f)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Buku bisnis",
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .size(32.dp)
                            )
                        },
                        shape = RoundedCornerShape(100),
                        border = BorderStroke(1.dp, Color1),
                        colors = if (selectedBookType == "Bisnis") {
                            AssistChipDefaults.assistChipColors(
                                containerColor = Color1,
                                labelColor = Color.White,
                                leadingIconContentColor = Color.White
                            )
                        } else {
                            AssistChipDefaults.assistChipColors(
                                containerColor = Color.Unspecified,
                                labelColor = Color1,
                                leadingIconContentColor = Color1
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {

                    CustomTextField(
                        label = "Nama Buku",
                        fieldValue = nameFieldValue,
                        onValueChange = { nameFieldValue = it },
                        hint = if (selectedBookType == "Bisnis") {
                            "Catatan Toko Saya"
                        } else {
                            "Catatan Harian"
                        }
                    )

                    CustomTextField(
                        label = "Deskripsi",
                        fieldValue = descriptionFieldValue,
                        onValueChange = { descriptionFieldValue = it },
                        hint = if (selectedBookType == "Bisnis") {
                            "Riwayat penjualan Toko Saya"
                        } else {
                            "Catatan harian pribadi saya"
                        }
                    )

                    if (selectedBookType == "Bisnis") {
                        CustomTextField(
                            label = "Alamat (opsional)",
                            fieldValue = addressFieldValue,
                            onValueChange = { addressFieldValue = it },
                            hint = "Alamat"
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "Kontak (opsional)",
                                color = Color2,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                            OutlinedTextField(
                                value = contactFieldValue,
                                onValueChange = { contactFieldValue = it },
                                placeholder = { Text(text = "No. HP", color = OptionalColor3) },
                                shape = RoundedCornerShape(12.dp),
                                maxLines = 1,
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = OptionalColor3,
                                    focusedBorderColor = Color2
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        if (
                            selectedBookType == "Pribadi"
                            && nameFieldValue.isNotEmpty()
                        ) {
                            val newBook = Book(
                                id = 0,
                                name = nameFieldValue,
                                type = BookType.PRIBADI,
                                description = descriptionFieldValue,
                                address = null,
                                phone = null
                            )

                            bookViewModel.addBook(newBook)

                            nameFieldValue = ""
                            descriptionFieldValue = ""
                            addressFieldValue = ""
                            contactFieldValue = ""

                            onConfirm()

                        } else if (selectedBookType == "Bisnis"
                            && nameFieldValue.isNotEmpty()
                        ) {
                            val newBook = Book(
                                id = 0,
                                name = nameFieldValue,
                                type = BookType.BISNIS,
                                description = descriptionFieldValue,
                                address = addressFieldValue,
                                phone = if (contactFieldValue.isNotEmpty()) {
                                    contactFieldValue.toLong()
                                } else {
                                    null
                                }
                            )

                            bookViewModel.addBook(newBook)

                            nameFieldValue = ""
                            descriptionFieldValue = ""
                            addressFieldValue = ""
                            contactFieldValue = ""

                            onConfirm()
                        } else {
                            Toast.makeText(context, "Harap isi semua kolom.", Toast.LENGTH_SHORT).show()
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color1
                    ),
                    contentPadding = PaddingValues(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp, vertical = 32.dp)
                ) {
                    Text(
                        text = "Simpan",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = ralewayFamily,
                        color = Color.White,
                    )
                }
            }
        }
    }
}

@Composable
fun BookListItem(
    book: Book,
    onClick: () -> Unit,
) {
    Column {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            shape = RoundedCornerShape(24),
            border = BorderStroke(1.dp, OptionalColor3),
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .clickable { onClick() }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.asians),
                    contentDescription = "Asians",
                    tint = Color(0xFFFFB921)
                )
                Text(
                    text = book.name,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    Icons.Rounded.KeyboardArrowDown,
                    contentDescription = "Book",
                    modifier = Modifier.rotate(270F)
                )
            }
        }
    }
}


