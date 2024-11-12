package com.example.msprocesamientopedidos.feign;

import com.example.msprocesamientopedidos.dto.carrito;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-carrito-service", path = "/carrito")
public interface CarritoFeign {
    @GetMapping("/{userId}")
    ResponseEntity<List<carrito>> listByUserId(@PathVariable Integer userId);
}
