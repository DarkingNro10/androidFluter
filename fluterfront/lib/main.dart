import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'bloc/producto_bloc.dart';
import 'repositories/producto_repository.dart';
import 'services/api_service.dart';
import 'screens/lista_productos_screen.dart';
import 'screens/agregar_producto_screen.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // Crear instancia de ApiService
    final apiService = ApiService('http://192.168.1.33:8080'); // Cambia la URL según tu servidor
    final productoRepository = ProductoRepository(apiService);

    return BlocProvider(
      create: (context) => ProductoBloc(productoRepository),
      child: MaterialApp(
        title: 'Gestión de Productos',
        theme: ThemeData(
          primarySwatch: Colors.blue,
        ),
        initialRoute: '/',
        routes: {
          // Ruta para la lista de productos
          '/': (context) => ListaProductosScreen(),
          // Ruta para agregar producto
          '/agregar': (context) => AgregarProductoScreen(),
        },
      ),
    );
  }
}
