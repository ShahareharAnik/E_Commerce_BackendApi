package com.anik.E_Commerce.controller;

import com.anik.E_Commerce.service.OrderService;
import com.anik.E_Commerce.response.GenericResponse;
import com.anik.E_Commerce.wrapper.OrderRequestWrapper;
import com.anik.E_Commerce.wrapper.OrderResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/orders" )
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping( "/place-order" )
    public GenericResponse placeOrder( @RequestBody OrderRequestWrapper orderRequestWrapper ) {
        return orderService.placeOrder( orderRequestWrapper.getCustomerId() );
    }

    @GetMapping( "/all-orders" )
    public List<OrderResponseWrapper> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping( "/approve-order" )
    public GenericResponse approveOrder( @RequestBody OrderRequestWrapper orderRequestWrapper ) {
        return orderService.approveOrder( orderRequestWrapper.getOrderId() );
    }
}
