package com.anik.E_Commerce.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OrderRequestWrapper {
    private Integer customerId;
    private Integer orderId;
}
