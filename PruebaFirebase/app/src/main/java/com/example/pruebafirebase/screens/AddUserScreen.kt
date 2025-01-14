package com.example.pruebafirebase.screens



import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun AddUserScreen() {
    var nombre by remember { mutableStateOf("") }
    val db = Firebase.firestore

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val user = hashMapOf("nombre" to nombre)
            db.collection("usuarios").add(user)
        }) {
            Text("Agregar Usuario")
        }
    }
}
