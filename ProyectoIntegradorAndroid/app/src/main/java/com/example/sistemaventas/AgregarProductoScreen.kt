    package com.example.sistemaventas
    
    import Producto
    import androidx.compose.foundation.layout.*
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.navigation.NavHostController
    
    @Composable
    fun AgregarProductoScreen(onAgregar: (Producto) -> Unit) {
        var nombre by remember { mutableStateOf("") }
        var costo by remember { mutableStateOf("") }
        var cantidadStock by remember { mutableStateOf("") }
        var categoriaId by remember { mutableStateOf("") }
        var descripcion by remember { mutableStateOf("") }
        var proveedor by remember { mutableStateOf("") }
    
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del Producto") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = costo,
                onValueChange = { costo = it },
                label = { Text("Costo") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = cantidadStock,
                onValueChange = { cantidadStock = it },
                label = { Text("Stock") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = categoriaId,
                onValueChange = { categoriaId = it },
                label = { Text("ID de la Categoría") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = proveedor,
                onValueChange = { proveedor = it },
                label = { Text("Proveedor") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    // Validación básica para evitar errores de conversión
                    if (nombre.isNotEmpty() && costo.isNotEmpty() && cantidadStock.isNotEmpty() && categoriaId.isNotEmpty()) {
                        val producto = Producto(
                            id = (1..1000).random(),  // Genera un ID aleatorio por ahora
                            nombre = nombre,
                            costo = costo,
                            categoriaId = categoriaId.toIntOrNull() ?: 0,  // Convertir a entero de forma segura
                            tasaIGV = null,
                            descripcion = descripcion,
                            codigoBarras = null,
                            cantidadStock = cantidadStock,
                            disponible = null,
                            proveedor = proveedor,
                            categoria = null  // Si no tienes la categoría definida aún
                        )
                        ProductoRepository.agregarProducto(producto) {
                            onAgregar(it!!)
                        }
                    } else {
                        // Mostrar mensaje de error o manejar la validación
                        println("Por favor, complete todos los campos correctamente")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar Producto")
            }
        }
    }
