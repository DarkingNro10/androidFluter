package com.example.sistemaventas

import Producto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductoApiService {

    @GET("producto")
    fun obtenerProductos(): Call<List<Producto>>

    @POST("producto")
    fun agregarProducto(@Body producto: Producto): Call<Producto>
}
