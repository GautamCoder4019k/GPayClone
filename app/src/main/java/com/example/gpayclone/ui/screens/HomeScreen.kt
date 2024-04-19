package com.example.gpayclone.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gpayclone.presenter.signIn.UserData

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen(userData: UserData = UserData("", "", "")) {
    val viewModel = HomeScreenViewModel()
    LaunchedEffect(Unit) {
        viewModel.addUser(userData.userId, userData.username ?: "")
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Card(modifier = Modifier.padding(16.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
                Text(text = userData.username!!)
                Row {

                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Add Amount")
                    }
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Check Balance")
                    }
                }
            }
        }
    }
}
