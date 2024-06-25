package com.anik.E_Commerce.wrapper;

import com.anik.E_Commerce.allenum.OrderType;
import com.anik.E_Commerce.table.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class OrderResponseWrapper {
    private List<Cart> cartItems;
    private OrderType status;
    private Double totalPrice;
    private Integer orderId;
    private Integer customerId;
}
