import '../models/producto.dart';

abstract class ProductoEvent {}

// Evento para obtener la lista de productos
class ObtenerProductos extends ProductoEvent {}

// Evento para agregar un nuevo producto
class AgregarProducto extends ProductoEvent {
  final Producto producto;

  AgregarProducto(this.producto);
}
