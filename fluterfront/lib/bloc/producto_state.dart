import '../models/producto.dart';

abstract class ProductoState {}

// Estado inicial
class ProductoInitial extends ProductoState {}

// Estado de carga
class ProductoLoading extends ProductoState {}

// Estado cuando se han cargado los productos correctamente
class ProductoLoaded extends ProductoState {
  final List<Producto> productos;

  ProductoLoaded(this.productos);
}

// Estado de error
class ProductoError extends ProductoState {
  final String error;

  ProductoError(this.error);
}
