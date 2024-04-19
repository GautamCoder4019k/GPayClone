package com.example.tunetracker.ui.search

import androidx.lifecycle.ViewModel
import com.example.gpayclone.presenter.signIn.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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

