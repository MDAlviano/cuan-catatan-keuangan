package com.cuan.catatankeuangan.presentation.utils

import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthHelper {
    val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
}
