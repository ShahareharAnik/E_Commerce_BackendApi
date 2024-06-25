package com.anik.E_Commerce.service;

import com.anik.E_Commerce.allreturns.FixedMessages;
import com.anik.E_Commerce.repositories.ProductRepository;
import com.anik.E_Commerce.specification.ProductSpecifications;
import com.anik.E_Commerce.table.Product;
import com.anik.E_Commerce.response.GenericResponse;
import com.anik.E_Commerce.wrapper.ProductRequestWrapper;
import com.anik.E_Commerce.wrapper.ProductResponseWrapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductResponseWrapper> getAllProductInfo() {
        return productRepository.findAll().stream().map( this::convertToProductResponseWrapper ).collect( Collectors.toList() );
    }

    public List<ProductResponseWrapper> getSortedByName( String name ) {
        return productRepository.findAll( ( root, query, cb ) -> cb.equal( root.get( "name" ), name ), Sort.by( "name" ) )
                .stream().map( this::convertToProductResponseWrapper ).collect( Collectors.toList() );
    }

    public List<ProductResponseWrapper> getSortedByPrice( Double price ) {
        return productRepository.findAll( ( root, query, cb ) -> cb.equal( root.get( "price" ), price ), Sort.by( "price" ) )
                .stream().map( this::convertToProductResponseWrapper ).collect( Collectors.toList() );
    }

    public List<ProductResponseWrapper> getSortedByProductID( Integer id ) {
        return productRepository.findAll( ( root, query, cb ) -> cb.equal( root.get( "id" ), id ), Sort.by( "id" ) )
                .stream().map( this::convertToProductResponseWrapper ).collect( Collectors.toList() );
    }

    @Transactional
    public GenericResponse addProduct( ProductRequestWrapper productRequestWrapper ) {
        Product product = new Product();
        product.setProductID( productRequestWrapper.getProductID() );
        product.setName( productRequestWrapper.getName() );
        product.setQuantity( productRequestWrapper.getQuantity() );
        product.setPrice( productRequestWrapper.getPrice() );
        productRepository.save( product );
        return new GenericResponse( product.getProductID(), FixedMessages.Product_added_successfully, HttpStatus.OK.value(), LocalDateTime.now() );
    }

    @Transactional
    public GenericResponse deleteProduct( int id ) {
        if ( productRepository.existsById( id ) ) {
            productRepository.deleteById( id );
            return new GenericResponse( id, FixedMessages.Product_deleted_successfully, HttpStatus.OK.value(), LocalDateTime.now() );
        }
        return new GenericResponse( id, FixedMessages.Product_Not_Found, HttpStatus.NOT_FOUND.value(), LocalDateTime.now() );
    }

    @Transactional
    public GenericResponse updateProduct( int id, ProductRequestWrapper productRequestWrapper ) {
        if ( productRepository.existsById( id ) ) {
            Product product = new Product();
            product.setProductID( id );
            product.setName( productRequestWrapper.getName() );
            product.setQuantity( productRequestWrapper.getQuantity() );
            product.setPrice( productRequestWrapper.getPrice() );
            productRepository.save( product );
            return new GenericResponse( id, FixedMessages.Product_updated_Successfully, HttpStatus.OK.value(), LocalDateTime.now() );
        }
        return new GenericResponse( id, FixedMessages.Product_Not_Found, HttpStatus.NOT_FOUND.value(), LocalDateTime.now() );
    }

    public Product getProductById( Integer id ) {
        Optional<Product> product = productRepository.findById( id );
        return product.orElse( null );
    }

    public Product getProductByName( String name ) {
        List<Product> products = productRepository.findByName( name );
        if ( products != null && !products.isEmpty() ) {
            return products.get( 0 );
        }
        return null;
    }

    public List<ProductResponseWrapper> findProducts( Integer id, String name, Double price, Sort sort ) {
        Specification<Product> spec = ProductSpecifications.hasId( id )
                .or( ProductSpecifications.hasName( name ) )
                .or( ProductSpecifications.hasPrice( price ) );

        return productRepository.findAll( spec, sort ).stream().map( this::convertToProductResponseWrapper ).collect( Collectors.toList() );
    }

    @Transactional
    public void updateProductQuantity( Integer productId, Integer quantityToReduce ) {
        Product product = productRepository.findById( productId ).orElse( null );
        if ( product != null ) {
            int updatedQuantity = product.getQuantity() - quantityToReduce;
            if ( updatedQuantity >= 0 ) {
                product.setQuantity( updatedQuantity );
                productRepository.save( product );
            } else {
                throw new IllegalArgumentException( "Insufficient quantity in stock for product: " + product.getName() );
            }
        } else {
            throw new IllegalArgumentException( "Product not found with ID: " + productId );
        }
    }

    private ProductResponseWrapper convertToProductResponseWrapper( Product product ) {
        return new ProductResponseWrapper( product.getProductID(), product.getName(), product.getQuantity(), product.getPrice() );
    }
}
