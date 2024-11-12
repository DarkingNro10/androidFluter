package com.example.mscarrito.feign;

import com.example.mscarrito.dto.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "producto-service", path = "/producto")
public interface ProductoFeign {
    @GetMapping("/{id}")
    ResponseEntity<Producto> listById(@PathVariable Integer id);
}
