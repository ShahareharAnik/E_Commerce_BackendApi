package com.anik.E_Commerce.specification;

import com.anik.E_Commerce.table.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

        public static Specification<User> hasName( String name ){
            if ( name == null )
                return Specification.where( null );


              return ( root, query, cb ) -> cb.equal(root.get( "name" ), name);
        }

        public static Specification<User> hasType( String type ){
            if ( type == null )
                return Specification.where( null );

             return (root, query, cb) -> cb.equal(root.get( "type" ), type);
        }

        public static Specification<User> hasId( Integer id ){
            if ( id == null )
                return Specification.where( null );


             return ( root, query, cb ) -> cb.equal(root.get( "id" ), id);
        }
}
