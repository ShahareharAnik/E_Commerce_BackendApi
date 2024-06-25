package com.anik.E_Commerce.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductRequestWrapper {
    private Integer productID;
    private String name;
    private Integer quantity;
    private Double price;
}
