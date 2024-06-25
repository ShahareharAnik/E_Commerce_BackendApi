package com.anik.E_Commerce.service;

import com.anik.E_Commerce.allreturns.FixedMessages;
import com.anik.E_Commerce.allenum.CartType;
import com.anik.E_Commerce.allenum.OrderType;
import com.anik.E_Commerce.repositories.CartRepository;
import com.anik.E_Commerce.repositories.OrderRepository;
import com.anik.E_Commerce.table.Cart;
import com.anik.E_Commerce.table.Order;
import com.anik.E_Commerce.response.GenericResponse;
import com.anik.E_Commerce.wrapper.OrderResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    @Transactional
    public GenericResponse placeOrder( Integer customerId ) {
        List<Cart> cartItems = cartRepository.findByCustomerId( customerId );

        List<Cart> cartItemsToPlaceOrder = cartItems.stream()
                .filter(cart -> CartType.WAIT_FOR_APPROVAL.equals( cart.getStatus() ))
                .collect(Collectors.toList());

        if (cartItemsToPlaceOrder.isEmpty())
            return new GenericResponse( FixedMessages.THERE_IS_NO_PRODUCT_AVAILABLE_TO_PLACE_ORDER, HttpStatus.BAD_REQUEST );

        Order order = new Order();
        order.setCustomerId(customerId);
        order.setStatus(OrderType.WAIT_FOR_APPROVAL);

        int totalQuantity = 0;
        double totalPrice = 0.0;

        for (Cart cartItem : cartItemsToPlaceOrder) {
            cartItem.setStatus( CartType.WAIT_FOR_APPROVAL );
            cartItem.setOrder(order);
            totalQuantity += cartItem.getQuantity();
            totalPrice += cartItem.getPrice() * cartItem.getQuantity();

            try {
                productService.updateProductQuantity(cartItem.getProductID(), cartItem.getQuantity());
            } catch (IllegalArgumentException e) {
                return new GenericResponse(null, e.getMessage(), HttpStatus.BAD_REQUEST );
            }
        }

        order.setTotalQuantity(totalQuantity);
        order.setTotalPrice(totalPrice);
        order.setCartItems(cartItemsToPlaceOrder);

        orderRepository.save(order);

        cartRepository.deleteAll(cartItemsToPlaceOrder);

        return new GenericResponse(order.getOrderId(), FixedMessages.ORDER_PLACED_AND_WAITING_FOR_APPROVAL, HttpStatus.OK.value(), LocalDateTime.now());
    }

    public List<OrderResponseWrapper> getAllOrders() {
        return orderRepository.findAll().stream().map( this::convertToOrderResponseWrapper ).collect( Collectors.toList() );
    }

    @Transactional
    public GenericResponse approveOrder( Integer orderId ) {
        Order order = orderRepository.findById( orderId ).orElse(null);

        if (order == null) {
            return new GenericResponse( orderId, "Order not found", HttpStatus.NOT_FOUND.value(), LocalDateTime.now() );
        }

        order.setStatus( OrderType.ORDER_PLACED_SUCCESSFULLY );

        for (Cart cartItem : order.getCartItems()) {
            cartItem.setStatus( CartType.ORDER_PLACED_SUCCESSFULLY );
        }

        orderRepository.save( order );
        cartRepository.saveAll( order.getCartItems() );

        return new GenericResponse( order.getOrderId(), FixedMessages.ORDER_APPROVED_SUCCESSFULLY, HttpStatus.OK.value(), LocalDateTime.now() );
    }

    private OrderResponseWrapper convertToOrderResponseWrapper( Order order ) {
        return new OrderResponseWrapper(
                order.getCartItems(),
                order.getStatus(),
                order.getTotalPrice(),
                order.getOrderId(),
                order.getCustomerId()
        );
    }
}

