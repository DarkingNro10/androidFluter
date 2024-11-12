import '../models/producto.dart';
import '../services/api_service.dart';

class ProductoRepository {
  final ApiService apiService;

  // Constructor que recibe ApiService
  ProductoRepository(this.apiService);

  // Obtener productos
  Future<List<Producto>> obtenerProductos() async {
    final data = await apiService.get('/producto');
    return (data as List).map((json) => Producto.fromJson(json)).toList();
  }

  // Agregar un producto
  Future<void> agregarProducto(Producto producto) async {
    await apiService.post('/producto', producto.toJson());
  }

  // Actualizar un producto (opcional)
  Future<void> actualizarProducto(Producto producto) async {
    await apiService.put('/producto/${producto.id}', producto.toJson());
  }

  // Eliminar un producto (opcional)
  Future<void> eliminarProducto(int id) async {
    await apiService.delete('/producto/$id');
  }
}
