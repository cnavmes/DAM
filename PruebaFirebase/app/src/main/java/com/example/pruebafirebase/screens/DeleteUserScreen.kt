package com.example.pruebafirebase.screens

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun DeleteUserScreen() {
    val db = Firebase.firestore
    val usuarios = remember { mutableStateOf<List<DocumentSnapshot>>(emptyList()) }

    // Cargar usuarios desde Firebase
    LaunchedEffect(Unit) {
        db.collection("usuarios").get().addOnSuccessListener { result ->
            usuarios.value = result.documents
        }
    }

    // Mostrar lista de usuarios con opción para eliminarlos
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(usuarios.value) { usuario ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Mostrar el nombre del usuario
                Text(usuario.getString("nombre") ?: "Sin nombre")

                // Botón para eliminar el usuario
                Button(onClick = {
                    db.collection("usuarios").document(usuario.id).delete()
                        .addOnSuccessListener {
                            // Actualizar la lista tras eliminar un usuario
                            usuarios.value = usuarios.value.filter { it.id != usuario.id }
                        }
                }) {
                    Text("Eliminar")
                }
            }
        }
    }
}
