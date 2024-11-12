class Producto {
  final int id;
  final String nombre;
  final String costo;
  final int categoriaId;
  final String? descripcion;

  Producto({
    required this.id,
    required this.nombre,
    required this.costo,
    required this.categoriaId,
    this.descripcion,
  });

  factory Producto.fromJson(Map<String, dynamic> json) {
    return Producto(
      id: json['id'],
      nombre: json['nombre'],
      costo: json['costo'],
      categoriaId: json['categoriaId'],
      descripcion: json['descripcion'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'nombre': nombre,
      'costo': costo,
      'categoriaId': categoriaId,
      'descripcion': descripcion,
    };
  }
}
