package com.anik.E_Commerce.allenum;

import lombok.Getter;

public enum OrderType {
    ORDER_PLACED_SUCCESSFULLY("Order Placed Successfully"),
    ADDED_TO_CART("Added To Cart"),
    IN_PROGRESS("In Progress"),
    WAIT_FOR_APPROVAL("Wait For Approval");

    @Getter
    private final String message;

    OrderType(String message) {
        this.message = message;
    }
}
