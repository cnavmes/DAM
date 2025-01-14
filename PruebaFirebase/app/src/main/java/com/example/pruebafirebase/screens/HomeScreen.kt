package com.example.pruebafirebase.screens



import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate("addUser") }) {
            Text("Agregar Usuario")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("deleteUser") }) {
            Text("Eliminar Usuario")
        }
    }
}
