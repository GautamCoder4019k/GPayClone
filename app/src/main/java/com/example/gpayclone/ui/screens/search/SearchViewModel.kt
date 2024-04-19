package com.example.tunetracker.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.gpayclone.presenter.signIn.UserData
import com.example.gpayclone.ui.screens.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

const val TAG = "Response"


class SearchViewModel @Inject constructor() :
    ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

//    init {
//        searchTracks("howling asca")
//    }



    suspend fun fetchUsers(firestore: FirebaseFirestore=FirebaseFirestore.getInstance()): List<String> {
        val userList = mutableListOf<String>()

        try {
            // Get a reference to the "users" collection
            val usersCollection = firestore.collection("users")

            // Fetch all user documents from Firestore
            val querySnapshot = usersCollection.get().await()

            // Iterate over each document in the query snapshot
            for (document in querySnapshot.documents) {
                // Convert the document to a User object and add it to the list
                val user = document.getString("userName")
                user?.let {
                    userList.add(it)
                }
            }
        } catch (e: Exception) {
            // Handle exceptions
            throw IllegalStateException("Failed to fetch users", e)
        }
        Log.d(TAG, "fetchUsers: $userList")
        return userList
    }

    private val _songs = MutableStateFlow(listOf<UserData>())
    val songs = searchText.combine(_songs) { text, songs ->
        if (text.isBlank()) {
            songs
        } else {
            songs
        }

    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun clearSearchText() {
        _searchText.value = ""
    }


}

