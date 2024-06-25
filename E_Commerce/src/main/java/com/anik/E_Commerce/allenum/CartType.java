package com.anik.E_Commerce.allenum;

import lombok.Getter;

public enum CartType {
    ADDED_TO_CART("Added To Cart"),
    WAIT_FOR_APPROVAL("Wait For Approval"),
    ORDER_PLACED_SUCCESSFULLY( "Order Place Successfully"),
    REMOVED_FROM_THE_CART("Removed From The Cart"),
    ACTIVE( "Active");
//    REMOVE( "Remove"),
//    PENDING( "Pending"),
//    PLACED("Placed"),
//    APPROVED("Approved");


    @Getter
    private String message;

    CartType( String message ){
        this.message = message;
    }

}
