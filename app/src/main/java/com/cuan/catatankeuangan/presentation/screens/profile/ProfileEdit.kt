package com.cuan.catatankeuangan.presentation.screens.profile

import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.presentation.components.CustomTextField
import com.cuan.catatankeuangan.presentation.components.TopBar
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.MainBgColor
import com.cuan.catatankeuangan.presentation.theme.ralewayFamily
import com.cuan.catatankeuangan.presentation.utils.getCustomTopPadding
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfileEdit(
    editProfileScreen: Boolean,
    onDismiss: () -> Unit,
    currentName: String,
    email: String,
    onNameUpdated: (String) -> Unit
) {
    val customTopPadding = getCustomTopPadding(16.dp)
    var name by remember { mutableStateOf(currentName) }

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
                    )
                    Box(
                        contentAlignment = Alignment.BottomEnd,
                        modifier = Modifier
                            .size(120.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
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
                                .background(Color(0xFF5D5FEF)),
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

                    Column(modifier = Modifier.padding(24.dp, 0.dp, 24.dp, 0.dp)) {
                        CustomTextField(
                            label = "Nama",
                            fieldValue = name,
                            onValueChange = { name = it },
                            hint = "Nama",
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Button(
                        onClick = {
                            FirebaseFirestore.getInstance()
                                .collection("users")
                                .document(email)
                                .update("name", name)
                                .addOnSuccessListener {
                                    onNameUpdated(name)
                                    onDismiss()
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
                            .align(Alignment.CenterHorizontally)
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