package com.example.msprocesamientopedidos.service;

import com.example.msprocesamientopedidos.entity.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoService {
    List<Pedido> listar();

    Pedido guardar(Pedido pedido);

    Pedido actualizar(Pedido pedido);

    Optional<Pedido> listarPorId(Integer id);

    void eliminarPorId(Integer id);

    Pedido procesarPedido(Integer userId); // Añadir este método
}
