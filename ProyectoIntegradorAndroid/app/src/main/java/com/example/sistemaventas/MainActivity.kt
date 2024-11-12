package com.example.sistemaventas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.sistemaventas.ui.theme.SistemaVentasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SistemaVentasTheme {
                val navController = rememberNavController()
                SistemaVentasApp(navController)
            }
        }
    }
}

@Composable
fun SistemaVentasApp(navController: androidx.navigation.NavHostController) {
    Surface(color = MaterialTheme.colorScheme.background) {
        DashboardScreen(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SistemaVentasTheme {
        val navController = rememberNavController()
        SistemaVentasApp(navController)
    }
}
