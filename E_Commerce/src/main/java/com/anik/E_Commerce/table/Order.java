package com.anik.E_Commerce.table;

import com.anik.E_Commerce.allenum.OrderType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table( name = "orders" )
public class Order {
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE )
    @Column( name = "order_id" )
    private Integer orderId;

    @Column( name = "customer_id" )
    private Integer customerId;

    @Column( name = "total_quantity" )
    private Integer totalQuantity;

    @Column( name = "total_price" )
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column( name = "status" )
    private OrderType status; // "Wait For Approval", "Order Placed Successfully"

    @JsonManagedReference
    @OneToMany( mappedBy = "order", cascade = CascadeType.ALL )
    private List<Cart> cartItems;
}
