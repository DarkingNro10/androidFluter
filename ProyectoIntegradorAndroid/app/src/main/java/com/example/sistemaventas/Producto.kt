data class Producto(
    val id: Int,
    val nombre: String,
    val costo: String,
    val categoriaId: Int,
    val tasaIGV: String?,
    val descripcion: String,
    val codigoBarras: String?,
    val cantidadStock: String?,
    val disponible: String?,
    val proveedor: String?,
    val categoria: Categoria?  // Incluye la clase Categoria
)

data class Categoria(
    val id: Int,
    val titulo: String,
    val descripccion: String
)
