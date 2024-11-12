package com.example.sistemaventas

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Inventory
import com.example.sistemaventas.ListaProductosScreen

@Composable
fun DashboardScreen(navController: NavHostController) {
    val items = listOf("Tienda", "Agregar Producto", "Inventario")
    var selectedItem by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            when (item) {
                                "Tienda" -> Icon(Icons.Filled.ShoppingCart, contentDescription = "Tienda")
                                "Agregar Producto" -> Icon(Icons.Filled.Add, contentDescription = "Agregar Producto")
                                "Inventario" -> Icon(Icons.Filled.Inventory, contentDescription = "Inventario")
                            }
                        },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            when (item) {
                                "Tienda" -> navController.navigate("lista_productos")
                                "Agregar Producto" -> navController.navigate("agregar_producto")
                                "Inventario" -> navController.navigate("inventario")
                            }
                        },
                        alwaysShowLabel = true
                    )
                }
            }
        }
    ) { paddingValues ->
        // Asegúrate de que el NavHost esté correctamente contenido
        NavHost(
            navController = navController,
            startDestination = "lista_productos",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("lista_productos") { ListaProductosScreen() }
            composable("agregar_producto") {
                AgregarProductoScreen(onAgregar = { producto ->
                    // Aquí puedes agregar la lógica para agregar el producto
                })
            }
            composable("inventario") { InventarioScreen() }
        }
    }
}
