package com.anik.E_Commerce.wrapper;

import com.anik.E_Commerce.allenum.CartType;
import com.anik.E_Commerce.table.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CartResponseWrapper {
    private Integer cartID;
    private Integer productID;
    private Integer customerId;
    private String name;
    private Integer quantity;
    private Double price;
    private CartType status;
    private Order order;
}
