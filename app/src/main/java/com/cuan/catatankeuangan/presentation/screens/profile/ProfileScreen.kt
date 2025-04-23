package com.cuan.catatankeuangan.presentation.screens.profile

import android.graphics.drawable.Icon
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color2
import com.cuan.catatankeuangan.presentation.theme.MainBgColor
import com.cuan.catatankeuangan.presentation.theme.OptionalColor4
import com.cuan.catatankeuangan.presentation.theme.OptionalColor3
import com.cuan.catatankeuangan.presentation.theme.VerticalGradient
import com.cuan.catatankeuangan.presentation.utils.getCustomTopPadding


@Composable
fun ProfileScreen(navController: NavController, bottomNavHeight: Dp) {

    val customTopPadding = getCustomTopPadding(16.dp)
    Box(modifier = Modifier.fillMaxSize()) {
        Column()
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(VerticalGradient(Color2, Color1))
                    .padding(24.dp, customTopPadding, 24.dp, 100.dp)
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
            Spacer(modifier = Modifier.height(40.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Lebaran James",
                    fontWeight = FontWeight.SemiBold
                ) // TODO: Change username dynamically
                Text(
                    text = "cuancuandancyonyacyonya@cuan.Com",
                    fontSize = 12.sp,
                    modifier = Modifier.offset(-3.dp)
                ) // TODO: Change email dynamically

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { navController.navigate("login") }) {
                    Text(text = "To login screen")
                }
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                ) {
                    Text(
                        text = "Akun",
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    ProfileItem2(
                        icon = painterResource(R.drawable.profile),
                        title = "Edit Profil",
                        onClick = {/*Navigate*/ })

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Toko", fontWeight = FontWeight.Bold)
                    ProfileItem2(
                        icon = painterResource(R.drawable.this_is_the_store_icon_fixed),
                        title = "Buku Digital",
                        onClick = {/*Navigate*/ })

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Lainnya",
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    SectionCardWithTwoButtons()
                }
            }


        }
        Image(painter = painterResource(R.drawable.ellipse_29),
            contentDescription = "",
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.TopCenter)
                .offset(y = 120.dp)
                .zIndex(1f)
            )
//        Icon(
//            painter = painterResource(R.drawable.ellipse_29),
//            contentDescription = "Foto Profil",
//            modifier = Modifier
//                .size(120.dp)
//                .align(Alignment.TopCenter)
//                .offset(y = 120.dp)
//                .zIndex(1f)
//        )
    }
}

@Composable
fun ProfileItem(icon: Painter, title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
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
                        .width(30.dp)
                        .width(49.dp)
                )
                Text(
                    text = title,
                    style = typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "", tint = Color.Black)
            }

        }
    }
}


@Composable
fun ProfileItem2(icon: Painter, title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .border(
                2.dp,
                color = OptionalColor3,
                RoundedCornerShape(8.dp)
            ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
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
                        .width(30.dp)
                        .width(49.dp)
                )
                Text(
                    text = title,
                    style = typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "", tint = Color.Black)
            }

        }
    }
}

@Composable
fun LogoutItem(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ikon_log_out_merah_buat_skrin_profil),
            contentDescription = null,
            tint = Color.Red,
            modifier = Modifier
                .width(30.dp)
                .width(49.dp)
        )

        Text(
            "Keluar Akun",
            color = Color.Red,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )

        Icon(Icons.Default.KeyboardArrowRight, contentDescription = "", tint = Color.Red)
    }
}

@Composable
fun SectionCardWithTwoButtons() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                2.dp,
                color = OptionalColor3,
                RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
    ) {
        // Item 1: Panduan
        ProfileItem(
            icon = painterResource(R.drawable.icon_buku_pandun_skrin_profil),
            title = "Panduan",
            onClick = {/*Navigate*/ })

        Divider(color = Color(0xFFF0F0F0), thickness = 1.dp) // optional separator

        // Item 2: Keluar Akun
        LogoutItem(onClick = { /* log out */ })
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