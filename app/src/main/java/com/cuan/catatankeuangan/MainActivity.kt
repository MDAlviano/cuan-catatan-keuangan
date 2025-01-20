package com.cuan.catatankeuangan

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cuan.catatankeuangan.data.local.entities.Product
import com.cuan.catatankeuangan.ui.theme.CuanTheme
import com.cuan.catatankeuangan.ui.theme.Typography
import com.cuan.catatankeuangan.viewmodel.ProductViewModel

class MainActivity : ComponentActivity() {
    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CuanTheme {
                Box(Modifier.safeDrawingPadding()) {
                    HomeScreen(productViewModel)
                }
            }
        }
    }
}


// Testing ROOM DB
@Composable
fun HomeScreen(productViewModel: ProductViewModel) {
    val products by productViewModel.allProducts.observeAsState(initial = emptyList())

    Column(modifier = Modifier.padding(16.dp)) {
        AddProductForm(productViewModel)
        Spacer(modifier = Modifier.height(16.dp))
        ProductList(products)
    }
}

@Composable
fun AddProductForm(productViewModel: ProductViewModel) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nama Produk") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Harga") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                val product = Product(
                    id = 0,
                    nama = name,
                    harga = price.toDoubleOrNull() ?: 0.0,
                )
                productViewModel.addProduct(product)
                name = ""
                price = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tambahkan produk")
        }
    }
}

@Composable
fun ProductList(products: List<Product>) {
    LazyColumn {
        items(products) { product ->
            ProductItem(product)
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 8.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = product.nama, style = Typography.bodyLarge)
            Text(text = product.harga.toString(), style = Typography.bodySmall)
        }
    }
}
