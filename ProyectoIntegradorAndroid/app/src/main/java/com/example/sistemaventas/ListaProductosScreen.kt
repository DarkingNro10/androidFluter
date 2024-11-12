package com.example.sistemaventas

import Producto
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.example.sistemaventas.ProductoRepository

@Composable
fun ListaProductosScreen() {
    var productos by remember { mutableStateOf<List<Producto>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            ProductoRepository.obtenerProductos { result ->
                if (result != null) {
                    productos = result
                }
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        productos.forEach { producto ->
            ProductoCard(producto = producto)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ProductoCard(producto: Producto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = producto.nombre, style = MaterialTheme.typography.titleLarge)
            Text(text = "Costo: ${producto.costo}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Stock: ${producto.cantidadStock}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
