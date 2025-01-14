package com.example.pruebafirebase


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pruebafirebase.screens.AddUserScreen
import com.example.pruebafirebase.screens.DeleteUserScreen
import com.example.pruebafirebase.screens.HomeScreen
import com.example.pruebafirebase.screens.LoginScreen
import com.example.pruebafirebase.screens.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen { navController.navigate("home") } }
        composable("home") { HomeScreen(navController) }
        composable("addUser") { AddUserScreen() }
        composable("deleteUser") { DeleteUserScreen() }
    }
}