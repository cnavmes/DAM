package com.example.pruebafirebase.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect



import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Bienvenido a Mi App", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("login")
    }
}
