package com.anik.E_Commerce.controller;

import com.anik.E_Commerce.allreturns.FixedMessages;
import com.anik.E_Commerce.service.CartService;
import com.anik.E_Commerce.service.ProductService;
import com.anik.E_Commerce.table.Product;
import com.anik.E_Commerce.wrapper.CartResponseWrapper;
import com.anik.E_Commerce.response.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping( "/cart/customer/{customerId}" )
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @PostMapping( "/add-product-by-id/{productId}" )
    public GenericResponse addProductToCartById( @PathVariable Integer customerId, @PathVariable Integer productId ) {
        Product product = productService.getProductById( productId );
        if ( product != null ) {
            return cartService.addProductToCart( customerId, product );
        } else {
            return new GenericResponse( productId, FixedMessages.Product_not_found_in_cart, HttpStatus.NOT_FOUND.value(), LocalDateTime.now() );
        }
    }

    @PostMapping( "/add-product-by-name/{productName}" )
    public GenericResponse addProductToCartByName( @PathVariable Integer customerId, @PathVariable String productName ) {
        Product product = productService.getProductByName( productName );
        if ( product != null ) {
            return cartService.addProductToCart( customerId, product );
        } else {
            return new GenericResponse( null, FixedMessages.Product_not_found_in_cart, HttpStatus.NOT_FOUND.value(), LocalDateTime.now() );
        }
    }

    @GetMapping
    public List<CartResponseWrapper> getAllCartItems( @PathVariable Integer customerId ) {
        return cartService.getAllCartItems( customerId );
    }

    @DeleteMapping( "/remove-product/{productId}" )
    public GenericResponse removeProductFromCart( @PathVariable Integer customerId, @PathVariable Integer productId ) {
        return cartService.removeProductFromCart( customerId, productId );
    }
}
