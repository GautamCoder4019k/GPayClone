package com.example.gpayclone.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

class HomeScreenViewModel : ViewModel() {

    val balance =MutableStateFlow("")
    val _balance =balance.asStateFlow()

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
    fun increaseBalance(userId: String, amount: Int, firestore: FirebaseFirestore=FirebaseFirestore.getInstance()) {
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

    suspend fun fetchBalance(userId: String, firestore: FirebaseFirestore=FirebaseFirestore.getInstance()):String {
        // Get a reference to the user document
        val userRef = firestore.collection("users").document(userId)

        try {
            // Get the user document from Firestore
            val snapshot = userRef.get().await()

            // Check if the user document exists
            if (snapshot.exists()) {
                // Extract and return the balance from the user document
               balance.value= snapshot.getLong("balance").toString()
                return snapshot.getLong("balance").toString()
            } else {
                // User document does not exist
                throw IllegalStateException("User document not found for ID: $userId")
            }
        } catch (e: Exception) {
            // Handle exceptions
            throw IllegalStateException("Failed to fetch balance for user ID: $userId", e)
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