package com.cuan.catatankeuangan.presentation.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.presentation.navigation.BottomBarScreen
import com.cuan.catatankeuangan.presentation.theme.MainBgColor
import com.cuan.catatankeuangan.presentation.utils.getCustomTopPadding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfileScreen(navController: NavController, bottomNavHeight: Dp) {
    val user = FirebaseAuth.getInstance().currentUser
    val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
    val displayName = user?.displayName ?: "Username"
    val email = user?.email ?: "Email"

    val customTopPadding = getCustomTopPadding(16.dp)

    LaunchedEffect(currentUserEmail) {
        currentUserEmail?.let {
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(it)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val name = document.getString("name") ?: ""
                        val email = document.getString("email") ?: ""
                        val profileUrl = document.getString("profilePictureUrl") ?: ""

                        // update UI
                    }
                }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainBgColor)
            .padding(0.dp, customTopPadding, 0.dp, bottomNavHeight),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.baseline_account_circle), // TODO: Change image dynamically
                contentDescription = "Account image",
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(100))
            )
//            Text(text = "Username", fontWeight = FontWeight.SemiBold) // TODO: Change username dynamically
//            Text(text = "User email") // TODO: Change email dynamically
            Text(text = displayName, fontWeight = FontWeight.SemiBold)
            Text(text = email)

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { navController.navigate("login") }) {
                Text(text = "To login screen")
            }
        }

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