package com.example.gpayclone.ui.screens

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class HomeScreenViewModel : ViewModel() {


    fun checkUserDocumentExists(
        userId: String,
        firestore: FirebaseFirestore,
        onComplete: (Boolean) -> Unit
    ) {
        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                // If the document exists, onComplete is called with true
                onComplete(document.exists())
            }
            .addOnFailureListener { exception ->
                // Handle failure
                onComplete(false)
            }
    }
    fun increaseBalance(userId: String, amount: Int, firestore: FirebaseFirestore) {
        // Update the balance field in the user document by incrementing it by the given amount
        val userRef = firestore.collection("users").document(userId)
        userRef.update("balance", amount)
            .addOnSuccessListener {
                // Balance updated successfully
            }
            .addOnFailureListener { e ->
                // Handle failure
            }
    }

    // Function to create a new user document in Firestore with initial balance 0
    fun createUserDocumentIfNotExists(userId: String,userName:String, firestore: FirebaseFirestore) {
        val user = hashMapOf(
            "userId" to userId,
            "userName" to userName,
            "balance" to 0
        )

        firestore.collection("users").document(userId)
            .set(user)
            .addOnSuccessListener {
                // Document successfully created
            }
            .addOnFailureListener { e ->
                // Handle failure
            }
    }

    // Usage
    // Replace with the actual user ID


    // Check if the user document exists
    fun addUser(userId: String,userName: String) {
        val firestore = FirebaseFirestore.getInstance()
        checkUserDocumentExists(userId, firestore) { exists ->
            if (!exists) {
                // If the document doesn't exist, create a new one
                createUserDocumentIfNotExists(userId, userName,firestore)
            }
        }
    }

}