    package com.example.sistemaventas

    import Producto
    import retrofit2.Call
    import retrofit2.Callback
    import retrofit2.Response

    object ProductoRepository {

        fun agregarProducto(producto: Producto, onResult: (Producto?) -> Unit) {
            RetrofitInstance.api.agregarProducto(producto).enqueue(object : Callback<Producto> {
                override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                    if (response.isSuccessful) {
                        onResult(response.body())
                    } else {
                        onResult(null)
                    }
                }

                override fun onFailure(call: Call<Producto>, t: Throwable) {
                    onResult(null)
                }
            })
        }

        fun obtenerProductos(onResult: (List<Producto>?) -> Unit) {
            RetrofitInstance.api.obtenerProductos().enqueue(object : Callback<List<Producto>> {
                override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                    if (response.isSuccessful) {
                        onResult(response.body())
                    } else {
                        onResult(null)
                    }
                }

                override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                    onResult(null)
                }
            })
        }
    }
