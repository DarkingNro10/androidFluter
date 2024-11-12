package com.example.mscarrito.service.Impl;

import com.example.mscarrito.entity.CarritoItem;
import com.example.mscarrito.feign.ProductoFeign;
import com.example.mscarrito.feign.UserFeign;
import com.example.mscarrito.dto.Producto;
import com.example.mscarrito.dto.AuthUser;
import com.example.mscarrito.repository.CarritoRepository;
import com.example.mscarrito.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoFeign productoFeign;

    @Autowired
    private UserFeign userFeign;

    @Override
    public CarritoItem guardarItemEnCarrito(CarritoItem item) {
        item.updateTimestamps();
        return carritoRepository.save(item);
    }

    @Override
    public List<CarritoItem> obtenerTodosLosItems() {
        return carritoRepository.findAll();
    }

    @Override
    public CarritoItem agregarItemAlCarrito(CarritoItem item) {
        // Usar Feign para obtener la información del producto
        Producto producto = productoFeign.listById(item.getProductId()).getBody();
        if (producto != null) {
            item.setProductName(producto.getNombre());
            item.updateTimestamps();
            // Asignar el costo del producto desde el servicio de productos
            item.setProductPrice(new BigDecimal(producto.getCosto()));
            item.setSubtotal(item.getProductPrice().multiply(new BigDecimal(item.getCantidad())));
        } else {
            throw new RuntimeException("Producto no encontrado");
        }

        // Usar Feign para obtener la información del usuario
        AuthUser user = userFeign.listById(item.getUserId()).getBody();
        if (user != null) {
            // Puedes utilizar la información del usuario si es necesario
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }

        return carritoRepository.save(item);
    }

    @Override
    public Optional<CarritoItem> listarPorId(Integer id) {
        return carritoRepository.findById(id);
    }

    @Override
    public void vaciarCarrito(Integer id) {
        carritoRepository.deleteById(id);
    }

    @Override
    public List<CarritoItem> obtenerItemsPorUsuario(Integer userId) {
        return carritoRepository.findByUserId(userId);
    }

    @Override
    public BigDecimal calcularPrecioTotalPorUsuario(Integer userId) {
        List<CarritoItem> items = carritoRepository.findByUserId(userId);
        return items.stream()
                .map(CarritoItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
