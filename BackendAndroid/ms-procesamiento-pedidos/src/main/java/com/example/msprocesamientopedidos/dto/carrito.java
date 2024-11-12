package com.example.msprocesamientopedidos.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class carrito {
    private Integer id;
    private Integer productId;
    private String productName;
    private BigDecimal productPrice;
    private int cantidad;
    private BigDecimal subtotal;
    private Integer userId;
}
