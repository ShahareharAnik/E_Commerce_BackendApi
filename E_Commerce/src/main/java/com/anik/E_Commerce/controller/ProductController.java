package com.anik.E_Commerce.controller;

import com.anik.E_Commerce.service.ProductService;
import com.anik.E_Commerce.response.GenericResponse;
import com.anik.E_Commerce.wrapper.ProductRequestWrapper;
import com.anik.E_Commerce.wrapper.ProductResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/products" )
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductResponseWrapper> getAllProductInfo() {
        return productService.getAllProductInfo();
    }

    @GetMapping( "/name/{name}" )
    public List<ProductResponseWrapper> getSortedByName( @PathVariable String name ) {
        return productService.getSortedByName( name );
    }

    @GetMapping( "/price/{price}" )
    public List<ProductResponseWrapper> getSortedByPrice( @PathVariable Double price ) {
        return productService.getSortedByPrice( price );
    }

    @GetMapping( "/id/{id}" )
    public List<ProductResponseWrapper> getSortedByProductID( @PathVariable Integer id ) {
        return productService.getSortedByProductID( id );
    }

    @PostMapping( "/add" )
    public GenericResponse addProduct( @RequestBody ProductRequestWrapper productRequestWrapper ) {
        return productService.addProduct( productRequestWrapper );
    }

    @DeleteMapping( "/delete/{id}" )
    public GenericResponse deleteProduct( @PathVariable int id ) {
        return productService.deleteProduct( id );
    }

    @PutMapping( "/update/{id}" )
    public GenericResponse updateProduct( @PathVariable int id, @RequestBody ProductRequestWrapper productRequestWrapper ) {
        return productService.updateProduct( id, productRequestWrapper );
    }

    @GetMapping( "/search" )
    public List<ProductResponseWrapper> searchProducts(
            @RequestParam( required = false ) Integer id,
            @RequestParam( required = false ) String name,
            @RequestParam( required = false ) Double price,
            Sort sort ) {
        return productService.findProducts( id, name, price, sort );
    }
}
