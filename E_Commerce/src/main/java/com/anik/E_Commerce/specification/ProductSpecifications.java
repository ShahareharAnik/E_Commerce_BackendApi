package com.anik.E_Commerce.specification;

import com.anik.E_Commerce.table.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> hasName( String name ) {
        if ( name == null )
            return Specification.where( null );
        return ( root, query, cb ) -> cb.equal( root.get( "name" ), name ) ;
    }

    public static Specification<Product> hasId( Integer id ) {
        if ( id == null )
            return Specification.where( null );

        return ( root, query, cb ) -> cb.equal( root.get( "id" ), id );
        }


    public static Specification<Product> hasPrice( Double price ) {
        if ( price == null )
            return Specification.where( null );

        return ( root, query, cb ) -> cb.equal( root.get ( "price" ), price );
    }
}
