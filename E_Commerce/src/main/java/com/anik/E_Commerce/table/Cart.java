package com.anik.E_Commerce.table;

import com.anik.E_Commerce.allenum.CartType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table( name = "cart" )
public class Cart {
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE )
    @Column( name = "cart_id" )
    private Integer cartID;

    @Column( name = "product_id" )
    private Integer productID;

    @Column( name = "customer_id" )
    private Integer customerId;

    @Column( name = "name" )
    private String name;

    @Column( name = "quantity" )
    private Integer quantity;

    @Column( name = "price" )
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column( name = "status" )
    private CartType status; // "Added to Cart", "Wait For Approval", "Order Placed Successfully"

    @JsonBackReference
    @ManyToOne
    @JoinColumn( name = "order_id" )
    private Order order;
}
