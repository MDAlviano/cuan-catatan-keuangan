package com.cuan.catatankeuangan.presentation.screens.product

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.data.local.entities.Product
import com.cuan.catatankeuangan.presentation.components.CurrencyTextField
import com.cuan.catatankeuangan.presentation.components.CustomTextField
import com.cuan.catatankeuangan.presentation.components.TopBar
import com.cuan.catatankeuangan.presentation.screens.product.category.NewCategoryDialog
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color2
import com.cuan.catatankeuangan.presentation.theme.MainBgColor
import com.cuan.catatankeuangan.presentation.theme.OptionalColor3
import com.cuan.catatankeuangan.presentation.theme.interFamily
import com.cuan.catatankeuangan.presentation.theme.ralewayFamily
import com.cuan.catatankeuangan.presentation.utils.formatNominal
import com.cuan.catatankeuangan.viewmodel.ProductViewModel
import java.io.File

@Composable
fun EditProduct(
    product: Product,
    productViewModel: ProductViewModel,
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val imagePath = remember { mutableStateOf<String?>(null) }

    val cropImageLauncher = rememberLauncherForActivityResult(
        contract = CropImageContract()
    ) { result ->
        if (result.isSuccessful) {
            val croppedUri = result.uriContent
            imageUri.value = croppedUri

            // Compress image and saving to local
            croppedUri?.let { uri ->
                val inputStream = context.contentResolver.openInputStream(uri)
                val file = File(context.filesDir, "product_${System.currentTimeMillis()}.jpg")

                inputStream?.use { input ->
                    val bitmap = BitmapFactory.decodeStream(input)
                    file.outputStream().use { output ->
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, output) // 60% quality
                    }
                }

                imagePath.value = file.absolutePath
            }
        } else {
            Log.e("Cropper", "Failed to crop: ${result.error}")
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            cropImageLauncher.launch(
                CropImageContractOptions(
                    uri,
                    CropImageOptions().apply {
                        aspectRatioX = 1
                        aspectRatioY = 1
                        fixAspectRatio = true
                        guidelines = CropImageView.Guidelines.ON
                    }
                )
            )
        }
    }

    if (showDialog) {

        val categoryList by productViewModel.allCategories.observeAsState(emptyList())

        // Form states
        var productNameValue by remember { mutableStateOf("") }
        var sellPriceValue by remember { mutableStateOf(TextFieldValue("")) }
        var buyPriceValue by remember { mutableStateOf(TextFieldValue("")) }

        val rawTotalAmount = remember { mutableStateOf("") }
        val rawTotalAmount2 = remember { mutableStateOf("") }

        // Category dropdown states
        var expanded by remember { mutableStateOf(false) }
        var showNewCategory by remember { mutableStateOf(false) }

        var selectedCategory by remember { mutableStateOf("Pilih Kategori") }
        var selectedCategoryId by remember { mutableStateOf<Int?>(null) }

        LaunchedEffect(product) {
            imageUri.value = null
            imagePath.value = null

            productNameValue = product.name
            sellPriceValue = TextFieldValue(product.sellPrice.toString())
            buyPriceValue = TextFieldValue(product.buyPrice.toString())
            rawTotalAmount.value = product.sellPrice.toString()
            rawTotalAmount2.value = product.buyPrice.toString()

            selectedCategoryId = product.categoryId
            selectedCategory =
                categoryList.find { it.id == product.categoryId }?.name ?: "Pilih Kategori"

            imagePath.value = product.imageUri
        }

        NewCategoryDialog(
            productViewModel = productViewModel,
            showDialog = showNewCategory,
            onDismiss = {
                showNewCategory = false
                imageUri.value = null
            }
        )

        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MainBgColor)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TopBar(onClick = onDismiss, text = "Edit Produk")

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF2F2F2))
                            .border(1.dp, OptionalColor3, RoundedCornerShape(12.dp))
                            .clickable { launcher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            val painter = when {
                                imageUri.value != null -> {
                                    rememberAsyncImagePainter(model = imageUri.value)
                                }

                                !product.imageUri.isNullOrEmpty() -> {
                                    rememberAsyncImagePainter(model = File(product.imageUri))
                                }

                                else -> {
                                    painterResource(id = R.drawable.no_image)
                                }
                            }

                            Image(
                                painter = painter,
                                contentDescription = "Upload image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                            if (imageUri.value == null && product.imageUri.isNullOrEmpty()) {
                                Text(
                                    text = "Unggah gambar produk",
                                    fontSize = 12.sp,
                                    color = OptionalColor3
                                )
                            }
                        }

                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Forms
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    ) {

                        CustomTextField(
                            label = "Nama Produk",
                            fieldValue = productNameValue,
                            onValueChange = { productNameValue = it },
                            hint = "Nama produk"
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        CurrencyTextField(
                            label = "Harga Jual (Satuan)",
                            fieldValue = sellPriceValue,
                            rawValue = rawTotalAmount,
                            onValueChange = { sellPriceValue = it }
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        CurrencyTextField(
                            label = "Harga Beli (Satuan)",
                            fieldValue = buyPriceValue,
                            rawValue = rawTotalAmount2,
                            onValueChange = { buyPriceValue = it }
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Kategori",
                            color = Color2,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Box(modifier = Modifier.fillMaxWidth()) {

                            OutlinedTextField(
                                value = selectedCategory,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = {
                                    IconButton(onClick = { expanded = !expanded }) {
                                        Icon(
                                            Icons.Default.ArrowDropDown,
                                            contentDescription = "Dropdown"
                                        )
                                    }
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = OptionalColor3,
                                    focusedBorderColor = Color2
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MainBgColor)
                            ) {

                                categoryList.forEach { category ->
                                    DropdownMenuItem(
                                        text = { Text(category.name) },
                                        onClick = {
                                            selectedCategory = category.name
                                            selectedCategoryId = category.id
                                            expanded = false
                                        }
                                    )
                                }

                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = "Kategori Baru",
                                            color = Color1,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    },
                                    onClick = { showNewCategory = true }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = {
                            val name = productNameValue.trim()
                            val sellPrice = rawTotalAmount.value.toLongOrNull() ?: 0L
                            val buyPrice = rawTotalAmount2.value.toLongOrNull() ?: 0L

                            if (name.isNotEmpty()
                                && sellPrice != 0L
                                && buyPrice != 0L
                                && selectedCategoryId != null
                            ) {
                                val updatedProduct = product.copy(
                                    name = name,
                                    sellPrice = sellPrice,
                                    buyPrice = buyPrice,
                                    categoryId = selectedCategoryId,
                                    imageUri = imagePath.value
                                )
                                productViewModel.updateProduct(updatedProduct)
                                onDismiss()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Harap isi semua kolom dengan benar.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
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
}