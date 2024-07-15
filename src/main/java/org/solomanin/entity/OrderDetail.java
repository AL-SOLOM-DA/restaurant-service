package org.solomanin.entity;

import org.solomanin.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public class OrderDetail {
    private Long id;
    private OrderStatus orderStatus;
    private List<Product> products;
    private BigDecimal totalAmount;

}
