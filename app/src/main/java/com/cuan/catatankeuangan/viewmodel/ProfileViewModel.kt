package com.cuan.catatankeuangan.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class ProfileViewModel : ViewModel() {

    var name by mutableStateOf("")
        private set

    fun onNameChange(newName: String) {
        name = newName
    }

    fun loadUserData(email: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(email).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    name = document.getString("name") ?: ""
                }
            }
    }

    fun updateUserName(email: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit = {}) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(email)
            .update("name", name)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }
}
