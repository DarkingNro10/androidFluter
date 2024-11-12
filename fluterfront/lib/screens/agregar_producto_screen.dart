import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../bloc/producto_bloc.dart';
import '../bloc/producto_event.dart';
import '../models/producto.dart';

class AgregarProductoScreen extends StatefulWidget {
  @override
  _AgregarProductoScreenState createState() => _AgregarProductoScreenState();
}

class _AgregarProductoScreenState extends State<AgregarProductoScreen> {
  final _formKey = GlobalKey<FormState>();
  String _nombre = '';
  String _costo = '';
  String _categoriaId = '';
  String _descripcion = '';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Agregar Producto'),
      ),
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: Column(
            children: [
              TextFormField(
                decoration: InputDecoration(labelText: 'Nombre'),
                onSaved: (value) => _nombre = value!,
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Por favor ingrese un nombre.';
                  }
                  return null;
                },
              ),
              TextFormField(
                decoration: InputDecoration(labelText: 'Costo'),
                keyboardType: TextInputType.number,
                onSaved: (value) => _costo = value!,
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Por favor ingrese el costo.';
                  }
                  return null;
                },
              ),
              TextFormField(
                decoration: InputDecoration(labelText: 'ID de Categoría'),
                keyboardType: TextInputType.number,
                onSaved: (value) => _categoriaId = value!,
              ),
              TextFormField(
                decoration: InputDecoration(labelText: 'Descripción'),
                onSaved: (value) => _descripcion = value!,
              ),
              SizedBox(height: 20),
              ElevatedButton(
                onPressed: () {
                  if (_formKey.currentState!.validate()) {
                    _formKey.currentState!.save();

                    // Crear el objeto Producto
                    final producto = Producto(
                      id: 0, // El ID se genera en el backend
                      nombre: _nombre,
                      costo: _costo,
                      categoriaId: int.tryParse(_categoriaId) ?? 0,
                      descripcion: _descripcion,
                    );

                    // Disparar el evento para agregar producto
                    context.read<ProductoBloc>().add(AgregarProducto(producto));

                    // Regresar a la pantalla anterior
                    Navigator.pop(context);
                  }
                },
                child: Text('Agregar Producto'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
