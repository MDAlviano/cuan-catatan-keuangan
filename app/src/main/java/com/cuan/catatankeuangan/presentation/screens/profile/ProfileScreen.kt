package com.cuan.catatankeuangan.presentation.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.presentation.screens.books.BookList
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color2
import com.cuan.catatankeuangan.presentation.theme.Color3
import com.cuan.catatankeuangan.presentation.theme.OptionalColor3
import com.cuan.catatankeuangan.presentation.theme.VerticalGradient
import com.cuan.catatankeuangan.presentation.theme.interFamily
import com.cuan.catatankeuangan.presentation.utils.getCustomTopPadding
import com.cuan.catatankeuangan.repository.BackupManager
import com.cuan.catatankeuangan.viewmodel.BookViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch


@Composable
fun ProfileScreen(
    navController: NavController,
    bottomNavHeight: Dp,
    bookViewModel: BookViewModel,
    backupManager: BackupManager
) {
    val user = FirebaseAuth.getInstance().currentUser
    val isLoggedIn = user != null
    val currentUserEmail = user?.email

    var name by remember { mutableStateOf("Username") }
    var email by remember { mutableStateOf("Email") }

    val coroutineScope = rememberCoroutineScope()

    // Fetch data from Firestore
    LaunchedEffect(currentUserEmail) {
        currentUserEmail?.let {
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(it)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        name = document.getString("name") ?: "Username"
                        email = document.getString("email") ?: "Email"
                    }
                }
        }
    }

    val customTopPadding = getCustomTopPadding(16.dp)

    var showEditProfileScreen by remember {
        mutableStateOf(false)
    }

    var digimon by remember { mutableStateOf(false) }

    ProfileEdit(
        editProfileScreen = showEditProfileScreen,
        onDismiss = { showEditProfileScreen = false }
    )

    BookList(
        showDialog = digimon,
        onDismiss = { digimon = false },
        bookViewModel = bookViewModel
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column() {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(VerticalGradient(Color2, Color1))
                    .padding(24.dp, customTopPadding, 24.dp, 80.dp)
            ) {
                Text(
                    text = "Profil",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                )
                Icon(
                    Icons.Default.Email,
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.CenterEnd),
                    tint = Color.White
                )
            }

            // Profile screen content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 120.dp, 24.dp, 12.dp)
            ) {
                Text(
                    text = "Akun",
                    fontWeight = FontWeight.SemiBold,
                    color = Color2
                )

                Spacer(modifier = Modifier.height(8.dp))
                ProfileItem(
                    icon = painterResource(R.drawable.profil_ini_buat_tombol_di_skrin_profil),
                    title = "Edit Profil",
                    shape = RoundedCornerShape(25f),
                    onClick = { showEditProfileScreen = true }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Buku",
                    fontWeight = FontWeight.SemiBold,
                    color = Color2
                )
                Spacer(modifier = Modifier.height(8.dp))
                ProfileItem(
                    icon = painterResource(R.drawable.this_is_the_store_icon_fixed),
                    title = "Buku Saya",
                    shape = RoundedCornerShape(25f),
                    onClick = { digimon = true }
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Lainnya",
                    fontWeight = FontWeight.SemiBold,
                    color = Color2
                )
                Spacer(modifier = Modifier.height(8.dp))

                SectionCardWithTwoButtons(
                    isLoggedIn = isLoggedIn,
                    onLogoutOrLoginClick = {
                        if (isLoggedIn) {
                            FirebaseAuth.getInstance().signOut()
                        }
                        navController.navigate("login") {
                            popUpTo("profile") { inclusive = true }
                        }
                    }
                )

                if (isLoggedIn) {
                    ProfileItem(
                        icon = painterResource(R.drawable.icon_buku_pandun_skrin_profil),
                        title = "Backup Data",
                        shape = RoundedCornerShape(25f),
                        onClick = {
                            coroutineScope.launch {
                                backupManager.backupAll(email)
                            }
                        }
                    )

                    ProfileItem(
                        icon = painterResource(R.drawable.icon_buku_pandun_skrin_profil),
                        title = "Restore Data",
                        shape = RoundedCornerShape(25f),
                        onClick = {
                            coroutineScope.launch {
                                backupManager.restoreAll(email)
                            }
                        }
                    )
                }

            }


        }

        //
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = customTopPadding)
                .align(Alignment.TopCenter)
                .offset(y = 40.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // User pfp
                Image(
                    painter = painterResource(R.drawable.ellipse_29),
                    contentDescription = "User profile picture",
                    modifier = Modifier
                        .size(120.dp)
                        .zIndex(1f)
                        .shadow(4.dp, RoundedCornerShape(100))
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Full name
                Text(
                    text = name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                ) // TODO: Change full name dynamically

                // Email
                Text(
                    text = email,
                    fontSize = 14.sp,
                    fontFamily = interFamily,
                    color = OptionalColor3
                ) // TODO: Change email dynamically
            }
        }
    }
}

@Composable
fun ProfileItem(
    icon: Painter,
    title: String,
    shape: Shape,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .border(
                1.dp,
                color = OptionalColor3,
                shape = shape
            ),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = icon, contentDescription = "", tint = Color.Black,
                    modifier = Modifier
                        .size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = title,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "", tint = Color1)
            }
        }
    }
}

@Composable
fun LoginItem(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth(),
        shape = RoundedCornerShape(bottomStart = 25f, bottomEnd = 25f),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.ikon_log_out_merah_buat_skrin_profil),
                    contentDescription = "",
                    tint = Color3,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Masuk",
                    fontWeight = FontWeight.Medium,
                    color = Color3,
                )
            }
        }
    }
}


@Composable
fun LogoutItem(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth(),
        shape = RoundedCornerShape(bottomStart = 25f, bottomEnd = 25f),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.ikon_log_out_merah_buat_skrin_profil),
                    contentDescription = "",
                    tint = Color3,
                    modifier = Modifier
                        .size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Keluar Akun",
                    fontWeight = FontWeight.Medium,
                    color = Color3,
                )
            }

        }
    }
}

@Composable
fun SectionCardWithTwoButtons(
    isLoggedIn: Boolean,
    onLogoutOrLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                color = OptionalColor3,
                RoundedCornerShape(25f)
            )
            .clip(RoundedCornerShape(25f))
            .background(Color.White)
    ) {
        // Item 1: Panduan
        ProfileItem(
            icon = painterResource(R.drawable.icon_buku_pandun_skrin_profil),
            title = "Panduan",
            shape = if (!isLoggedIn) RoundedCornerShape(25f) else RoundedCornerShape(topStart = 25f, topEnd = 25f),
            onClick = { /* Navigate */ }
        )

        if (isLoggedIn) {
            // Jika user login, tampilkan tombol logout
            LogoutItem(onClick = onLogoutOrLoginClick)
        } else {
            // Jika belum login, tampilkan tombol login
            LoginItem(onClick = onLogoutOrLoginClick)
        }

    }
}

