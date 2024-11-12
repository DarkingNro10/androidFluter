package com.example.msprocesamientopedidos.service.Impl;

import com.example.msprocesamientopedidos.dto.Cliente;
import com.example.msprocesamientopedidos.dto.Producto;
import com.example.msprocesamientopedidos.dto.carrito;
import com.example.msprocesamientopedidos.entity.Pedido;
import com.example.msprocesamientopedidos.entity.PedidoDetalle;
import com.example.msprocesamientopedidos.feign.ClienteFeign;
import com.example.msprocesamientopedidos.feign.ProductoFeign;
import com.example.msprocesamientopedidos.feign.CarritoFeign;
import com.example.msprocesamientopedidos.repository.PedidoRepository;
import com.example.msprocesamientopedidos.service.PdfGeneratorService;
import com.example.msprocesamientopedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteFeign clienteFeign;

    @Autowired
    private ProductoFeign productoFeign;

    @Autowired
    private CarritoFeign carritoFeign;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @Override
    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido guardar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido actualizar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public Optional<Pedido> listarPorId(Integer id) {
        Pedido pedido = pedidoRepository.findById(id).get();
        Cliente cliente = clienteFeign.listById(pedido.getClienteId()).getBody();
        List<PedidoDetalle> pedidoDetalles = pedido.getDetalle().stream().map(pedidoDetalle -> {
            Producto producto = productoFeign.listById(pedidoDetalle.getProductoId()).getBody();
            pedidoDetalle.setProducto(producto);
            return pedidoDetalle;
        }).collect(Collectors.toList());
        pedido.setDetalle(pedidoDetalles);
        pedido.setCliente(cliente);
        return Optional.of(pedido);
    }


    @Override
    public void eliminarPorId(Integer id) {
        pedidoRepository.deleteById(id);
    }

    @Override
    public Pedido procesarPedido(Integer userId) {
        // Obtener ítems del carrito
        List<carrito> cartItems = carritoFeign.listByUserId(userId).getBody();

        // Obtener información del cliente
        Cliente cliente = clienteFeign.listById(userId).getBody();

        // Calcular el monto total
        BigDecimal totalAmount = cartItems.stream()
                .map(carrito::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Crear el pedido
        Pedido pedido = new Pedido();
        pedido.setClienteId(userId);
        pedido.setTotalAmount(totalAmount);

        // Crear los detalles del pedido
        List<PedidoDetalle> pedidoDetalles = cartItems.stream().map(item -> {
            PedidoDetalle detalle = new PedidoDetalle();
            detalle.setProductoId(item.getProductId());
            detalle.setCantidad(item.getCantidad());
            detalle.setSubtotal(item.getSubtotal());
            detalle.setPedido(pedido);
            return detalle;
        }).collect(Collectors.toList());

        pedido.setDetalle(pedidoDetalles);

        // Guardar el pedido en la base de datos
        Pedido savedPedido = pedidoRepository.save(pedido);

        // Generar PDF
        pdfGeneratorService.generatePdf(savedPedido, "pedido_" + savedPedido.getId() + ".pdf");

        return savedPedido;
    }
}
