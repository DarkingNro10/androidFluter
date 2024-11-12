package com.example.mscarrito.repository;

import com.example.mscarrito.entity.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CarritoRepository extends JpaRepository<CarritoItem, Integer> {
    List<CarritoItem> findByUserId(Integer userId);
}
