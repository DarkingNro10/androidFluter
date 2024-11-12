import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../bloc/producto_bloc.dart';
import '../bloc/producto_event.dart';
import '../bloc/producto_state.dart';

class ListaProductosScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // Dispara el evento para obtener productos al cargar la pantalla
    context.read<ProductoBloc>().add(ObtenerProductos());

    return Scaffold(
      appBar: AppBar(
        title: Text('Lista de Productos'),
      ),
      body: BlocBuilder<ProductoBloc, ProductoState>(
        builder: (context, state) {
          if (state is ProductoLoading) {
            return Center(child: CircularProgressIndicator());
          } else if (state is ProductoLoaded) {
            final productos = state.productos;
            return ListView.builder(
              itemCount: productos.length,
              itemBuilder: (context, index) {
                final producto = productos[index];
                return Card(
                  margin: EdgeInsets.all(8.0),
                  child: ListTile(
                    title: Text(producto.nombre),
                    subtitle: Text('Costo: ${producto.costo}'),
                  ),
                );
              },
            );
          } else if (state is ProductoError) {
            return Center(child: Text(state.error));
          }
          return Center(child: Text('No hay productos disponibles.'));
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          Navigator.pushNamed(context, '/agregar');
        },
        child: Icon(Icons.add),
      ),
    );
  }
}
