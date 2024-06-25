package com.anik.E_Commerce.repositories;

import com.anik.E_Commerce.table.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByCustomerId(Integer customerId);
    List<Cart> findByCustomerIdAndProductID(Integer customerId, Integer productId);
    List<Cart> findByCustomerIdAndStatus(Integer customerId, String status);
    List<Cart> findByProductID(Integer productId);
}
