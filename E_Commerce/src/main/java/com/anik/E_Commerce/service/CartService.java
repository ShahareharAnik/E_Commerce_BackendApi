package com.anik.E_Commerce.service;

import com.anik.E_Commerce.allreturns.FixedMessages;
import com.anik.E_Commerce.allenum.OrderType;
import com.anik.E_Commerce.repositories.CartRepository;
import com.anik.E_Commerce.repositories.OrderRepository;
import com.anik.E_Commerce.table.Cart;
import com.anik.E_Commerce.table.Order;
import com.anik.E_Commerce.table.Product;
import com.anik.E_Commerce.wrapper.CartResponseWrapper;
import com.anik.E_Commerce.response.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.anik.E_Commerce.allenum.CartType.ADDED_TO_CART;
import static com.anik.E_Commerce.allenum.CartType.ORDER_PLACED_SUCCESSFULLY;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public GenericResponse addProductToCart( Integer customerId, Product product ) {
        // Check if there is an existing order for the customer with status "In Progress"
        Optional<Order> existingOrder = orderRepository.findByCustomerIdAndStatus( customerId, OrderType.IN_PROGRESS );
        Order order;

        if ( existingOrder.isPresent() ) {
            order = existingOrder.get();
        }
        else {
            // Create a new order if none exists
            order = new Order();
            order.setCustomerId( customerId );
            order.setStatus( OrderType.ADDED_TO_CART );
            order = orderRepository.save( order );
        }

        List<Cart> cartItems = cartRepository.findByCustomerIdAndProductID( customerId, product.getProductID() );
        if ( !cartItems.isEmpty() ) {
            Cart cartItem = cartItems.get( 0 );
            cartItem.setQuantity( cartItem.getQuantity() + 1 );
            cartRepository.save( cartItem );
        }
        else {
            Cart cart = new Cart();
            cart.setCustomerId( customerId );
            cart.setProductID( product.getProductID() );
            cart.setName( product.getName() );
            cart.setQuantity( 1 ); // Default quantity set to 1
            cart.setPrice( product.getPrice() );
            cart.setStatus( ADDED_TO_CART );
            cart.setOrder( order );
            cartRepository.save( cart );
        }

        // Update the total price and total quantity in the order
        updateOrderTotals( order );

        return new GenericResponse( order.getOrderId(), FixedMessages.Product_added_successfully, HttpStatus.OK.value(), LocalDateTime.now() );
    }

    public List<CartResponseWrapper> getAllCartItems( Integer customerId ) {
        List<Cart> cartItems = cartRepository.findByCustomerId( customerId );
        return cartItems.stream()
                .filter( cartItem -> !ORDER_PLACED_SUCCESSFULLY.equals( cartItem.getStatus() ) )
                .map( this::convertToCartResponseWrapper )
                .collect( Collectors.toList() );
    }

    public GenericResponse removeProductFromCart( Integer customerId, Integer productId ) {
        List<Cart> cartItems = cartRepository.findByCustomerIdAndProductID( customerId, productId );
        if ( !cartItems.isEmpty() ) {
            cartRepository.deleteAll( cartItems );

            // Update the total price and total quantity in the order
            Order order = cartItems.get( 0 ).getOrder();
            if ( order != null ) {
                updateOrderTotals( order );
            }

            return new GenericResponse( order.getOrderId(), FixedMessages.Product_removed_from_cart, HttpStatus.OK.value(), LocalDateTime.now() );
        } else {
            return new GenericResponse( null, FixedMessages.Product_not_found_in_cart, HttpStatus.NOT_FOUND.value(), LocalDateTime.now() );
        }
    }

    private void updateOrderTotals( Order order ) {
        List<Cart> cartItems = cartRepository.findByCustomerId( order.getCustomerId() );
        int totalQuantity = 0;
        double totalPrice = 0.0;

        for ( Cart cartItem : cartItems ) {
            totalQuantity += cartItem.getQuantity();
            totalPrice += cartItem.getPrice() * cartItem.getQuantity();
        }

        order.setTotalQuantity( totalQuantity );
        order.setTotalPrice( totalPrice );
        orderRepository.save( order );
    }

    private CartResponseWrapper convertToCartResponseWrapper( Cart cart ) {
        return new CartResponseWrapper(
                cart.getCartID(),
                cart.getProductID(),
                cart.getCustomerId(),
                cart.getName(),
                cart.getQuantity(),
                cart.getPrice(),
                cart.getStatus(),
                cart.getOrder()
        );
    }
}
