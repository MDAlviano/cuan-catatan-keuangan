package com.cuan.catatankeuangan.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.cuan.catatankeuangan.presentation.components.TopBar
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color2
import com.cuan.catatankeuangan.presentation.theme.MainBgColor
import com.cuan.catatankeuangan.presentation.theme.ralewayFamily
import com.cuan.catatankeuangan.presentation.utils.getCustomTopPadding
import com.cuan.catatankeuangan.repository.BackupManager
import kotlinx.coroutines.launch

@Composable
fun BackupRestoreDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    email: String,
    backupManager: BackupManager
) {
    val customTopPadding = getCustomTopPadding(16.dp)
    val coroutineScope = rememberCoroutineScope()

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
                Column(modifier = Modifier.fillMaxWidth()) {
                    TopBar(
                        onClick = onDismiss,
                        text = "Backup / Restore Data"
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Backup Semua Data (Buku, Transaksi, Produk)",
                            fontWeight = FontWeight.SemiBold,
                            color = Color2
                        )

                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    backupManager.backupAll(email)
                                }
                            },
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                containerColor = Color1
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 60.dp, vertical = 32.dp
                                )
                        ) {
                            Text(
                                "Backup",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = ralewayFamily,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Pulihkan Semua Data (Buku, Transaksi, Produk)",
                            fontWeight = FontWeight.SemiBold,
                            color = Color2
                        )

                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    backupManager.restoreAll(email)
                                }
                            },
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                containerColor = Color1
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 60.dp, vertical = 32.dp
                                )
                        ) {
                            Text(
                                "Restore",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = ralewayFamily,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

