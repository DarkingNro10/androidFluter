package com.example.mscarrito.feign;

import com.example.mscarrito.dto.AuthUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-cliente-service", path = "/cliente")
public interface UserFeign {
    @GetMapping("/{id}")
    ResponseEntity<AuthUser> listById(@PathVariable Integer id);
}
