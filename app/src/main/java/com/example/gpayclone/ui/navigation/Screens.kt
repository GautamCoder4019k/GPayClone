package com.example.gpayclone.ui.navigation

sealed class Screens(val route: String) {
    data object Home : Screens("HomeRoute")
    data object Transactions : Screens("TransactionRoute")
    data object Profile : Screens("ProfileRoute")
    data object Search : Screens("SearchScreen")

}
