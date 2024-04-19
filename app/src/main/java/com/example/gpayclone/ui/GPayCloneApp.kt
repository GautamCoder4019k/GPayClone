package com.example.gpayclone.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gpayclone.presenter.signIn.UserData
import com.example.gpayclone.ui.navigation.BottomNavigationItem
import com.example.gpayclone.ui.navigation.Screens
import com.example.gpayclone.ui.screens.HomeScreen
import com.example.gpayclone.ui.screens.ProfileScreen
import com.example.tunetracker.ui.search.SearchScreen

@Composable
fun GPayCloneApp(userData: UserData?,onSignOut:()->Unit) {
    var navigationSelectionItem by remember {
        mutableIntStateOf(0)
    }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {


            NavigationBar(containerColor = Color.Transparent) {
                BottomNavigationItem().bottomNavigationItems()
                    .forEachIndexed { index, navigationItem ->
                        NavigationBarItem(
                            selected = index == navigationSelectionItem,
                            label = { Text(text = navigationItem.label) },
                            icon = {
                                Icon(
                                    imageVector = navigationItem.icon,
                                    contentDescription = navigationItem.label
                                )
                            },
                            onClick = {
                                navigationSelectionItem = index
                                navController.navigate(navigationItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }


            }

        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Screens.Home.route) {
                HomeScreen(userData?:UserData("","",""))
            }
            composable(route = Screens.Search.route) {
                SearchScreen()
            }
            composable(route = Screens.Profile.route) {
                ProfileScreen(
                    userData = userData,
                    onSignOut = onSignOut
                )
            }
            composable(route = Screens.Transactions.route) {


            }

        }
    }
}