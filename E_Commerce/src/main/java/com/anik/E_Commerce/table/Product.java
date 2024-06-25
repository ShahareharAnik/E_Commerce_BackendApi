package com.anik.E_Commerce.table;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;

@Data
@Entity
@Table( name = "products" )
public class Product {
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE )
    @Column( name = "product_id" )
    private Integer productID;

    @Column( name = "name" )
    private String name;

    @Column( name = "quantity" )
    private Integer quantity;

    @Column( name = "price" )
    private Double price;
}
