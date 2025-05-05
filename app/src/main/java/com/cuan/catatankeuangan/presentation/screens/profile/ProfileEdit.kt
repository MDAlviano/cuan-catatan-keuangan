package com.cuan.catatankeuangan.presentation.screens.profile

import android.R.attr.description
import android.graphics.drawable.Icon
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.presentation.components.CustomTextField
import com.cuan.catatankeuangan.presentation.components.TopBar
import com.cuan.catatankeuangan.presentation.navigation.BottomBarScreen
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color2
import com.cuan.catatankeuangan.presentation.theme.MainBgColor
import com.cuan.catatankeuangan.presentation.theme.OptionalColor4
import com.cuan.catatankeuangan.presentation.theme.OptionalColor3
import com.cuan.catatankeuangan.presentation.theme.VerticalGradient
import com.cuan.catatankeuangan.presentation.theme.ralewayFamily
import com.cuan.catatankeuangan.presentation.utils.getCustomTopPadding


@Composable
fun ProfileEdit(
    editProfileScreen: Boolean,
    onDismiss: () -> Unit,

    ) {
    val customTopPadding = getCustomTopPadding(16.dp)
    if (editProfileScreen) {

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
                        text = "Edit Profil"
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()


                    ) {

//                    Icon(
//                        Icons.Default.ArrowBack,
//                        onClick = {navController.navigate(BottomBarScreen.Profile.route)},
//                        contentDescription = "",
//                        modifier = Modifier.align(Alignment.TopStart)
//                    )
//                    Text(
//                        text = "Edit Profil",
//                        fontWeight = FontWeight.SemiBold,
//                        modifier = Modifier
//                            .align(Alignment.TopCenter),
//                    )
                    }
                    Box(contentAlignment = Alignment.BottomEnd,
                        modifier = Modifier
                            .size(120.dp)
                            .align(Alignment.CenterHorizontally)) {
                        Image(
                            painter = painterResource(R.drawable.ellipse_29),
                            contentDescription = "",
                            modifier = Modifier
                                .height(140.dp)
                                .width(140.dp)
                                .align(Alignment.TopCenter)

                        )
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF5D5FEF)), // Warna ungu dari desain
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.kamera_buat_skrin_edit_profil),
                                contentDescription = "Edit Photo",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Column (modifier = Modifier.padding(24.dp,0.dp,24.dp,0.dp)) {

                        CustomTextField(
                            label = "Nama",
                            fieldValue = "description",
                            onValueChange = {
                                "description"
                            },
                            hint = "Nama",

                            )
                        Spacer(modifier = Modifier.height(8.dp))

                        CustomTextField(
                            label = "Email",
                            fieldValue = "description",
                            onValueChange = {
                                "description"
                            },
                            hint = "Jl. a",

                            )
                        Spacer(modifier = Modifier.height(8.dp))

                        CustomTextField(
                            label = "Alamat(Opsional)",
                            fieldValue = "description",
                            onValueChange = {
                                "description"
                            },
                            hint = "cuan@Cuan.com",

                            )
                        Spacer(modifier = Modifier.height(8.dp))

                        CustomTextField(
                            label = "Kontak(Opsional)",
                            fieldValue = "description",
                            onValueChange = {
                                "description"
                            },
                            hint = "088808880888",

                            )
                    }


                    }
                    Button(
                        onClick = {/*Todo*/},
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = Color1
                        ),
                        contentPadding = PaddingValues(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 60.dp, vertical = 32.dp
                            )
                            .align(Alignment.BottomCenter)) {
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

