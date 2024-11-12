package com.example.mscarrito.service;

import com.example.mscarrito.entity.CarritoItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CarritoService {
    CarritoItem guardarItemEnCarrito(CarritoItem item);
    List<CarritoItem> obtenerTodosLosItems();
    CarritoItem agregarItemAlCarrito(CarritoItem item);
    Optional<CarritoItem> listarPorId(Integer id);
    void vaciarCarrito(Integer id);
    List<CarritoItem> obtenerItemsPorUsuario(Integer userId);
    BigDecimal calcularPrecioTotalPorUsuario(Integer userId);
}
