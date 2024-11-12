package com.example.mscarrito.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class CarritoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer productId;
    private String productName;
    private BigDecimal productPrice;
    private int cantidad;
    private BigDecimal subtotal;

    private Integer userId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BigDecimal getSubtotal() {
        return productPrice.multiply(new BigDecimal(cantidad));
    }

    // Puedes agregar un método para actualizar la fecha de actualización
    public void updateTimestamps() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        updatedAt = LocalDateTime.now();
    }
}
